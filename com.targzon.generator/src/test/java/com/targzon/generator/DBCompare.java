package com.targzon.generator;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.targzon.generator.comp.Column;
import com.targzon.generator.comp.Table;
import com.targzon.generator.util.PropertiesUtil;

/**
 * 比较数据库差异字段、类型、长度
 * @author cqtoo
 *
 */
public class DBCompare {

	public static final String PRI = "PRI";
	public static final String NO = "NO";
	public static final String YES = "YES";
	public static final String NOT_NULL = "NOT NULL";
	public static final String DEFAULT_NULL = "DEFAULT NULL";
	
	
	//base_db信息
	private static final String driver1 = PropertiesUtil.getString("driver1");
	private static final String database1 = PropertiesUtil.getString("database1");
	private static final String url1 = PropertiesUtil.getString("url1");
	private static final String username1 = PropertiesUtil.getString("username1");
	private static final String password1 = PropertiesUtil.getString("password1");
	private static Connection connection1;
	private static PreparedStatement pstmt1;
	private static ResultSet rs1;
	
	//comp_db信息
	private static final String driver2 = PropertiesUtil.getString("driver2");
	private static final String database2 = PropertiesUtil.getString("database2");
	private static final String url2 = PropertiesUtil.getString("url2");
	private static final String username2 = PropertiesUtil.getString("username2");
	private static final String password2 = PropertiesUtil.getString("password2");
	private static Connection connection2;
	private static PreparedStatement pstmt2;
	private static ResultSet rs2;
	
	//SQL区
	private static String selectAllTables = "SELECT table_name FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = '%s'";
	private static String descColumns = "desc %s";
	private static String createTableSQL = "show create table %s";
	private static String columnsComment = "select Column_name,Column_comment from INFORMATION_SCHEMA.Columns where table_name='%s'  and table_schema='%s'";
	
	
	static{
		try {
			Class.forName(driver1);
			if(driver1!=null && !driver1.equals(driver2)){
				Class.forName(driver2);
			}
			connection1 = DriverManager.getConnection(url1,username1,password1);
			connection2 = DriverManager.getConnection(url2,username2,password2);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Map<String, Table> table222 = getTablesByDB(connection1, pstmt1, rs1, database1);
		Map<String, Table> table170 = getTablesByDB(connection2, pstmt2, rs2, database2);
		Map<String, Table> difference = comp(table222,table170);
		StringBuffer generatorSQL = generatorSQL(difference);
		System.out.println("生成可执行SQL：\n"+generatorSQL);

		if(connection1!=null){
			connection1.close();
		}
		if(connection2!=null){
			connection2.close();
		}
	}
	
	 /**
	  * @Description ：xx
	  * @param       ： xx
	  * @return      ： xx
	  * @throws      ： []
	  * @author      ： lt
	 * @throws SQLException 
	  * @Date        ：2016年10月24日下午7:27:53
	  */
	private static StringBuffer generatorSQL(Map<String, Table> difference) throws SQLException {
		StringBuffer generatorSQL = new StringBuffer();
		for (Entry<String, Table> diff : difference.entrySet()) {
			Table table = diff.getValue();
			if(table.isNewTable()){
				//1.新表
//				System.out.println("1.新表->"+table.getName());
				appendNewTableSQL(connection1, pstmt1, rs1, table.getName(),generatorSQL);
			}else{
				Map<String,Column> cols = table.getColunms();
				for (Entry<String, Column> diffCol : cols.entrySet()) {
					Column column = diffCol.getValue();
					if(column.isNewColumn()){
						//2.新字段
//						System.out.println("2.新字段->"+table.getName()+"."+column.getField());
						appendColumnSQL(table.getName(), column, generatorSQL);
					}else if(column.isNewProp()){
						//3.新属性
//						System.out.println("3.新属性->"+table.getName()+"."+column.getField());
						appendColumnPropSQL(table.getName(), column, generatorSQL);
					}else{
						//4.其他
						System.out.println("4.其他->"+table.getName()+"-->"+JSON.toJSONString(column));
					}
				}
			}
		}
		return generatorSQL;
	}

	/**
	  * @Description ：xx
	  * @param       ： xx
	  * @return      ： xx
	  * @throws      ： []
	  * @author      ： lt
	  * @throws SQLException 
	  * @Date        ：2016年10月24日下午7:55:59
	  */
	private static void appendNewTableSQL(Connection conn, PreparedStatement pstmt, ResultSet rs,
			String tableName,StringBuffer sb) throws SQLException {
		try {
			pstmt = conn.prepareStatement(String.format(createTableSQL, tableName));
			rs = pstmt.executeQuery();
			while(rs.next()){
				String sql = rs.getString(2);
				if(StringUtils.isNotBlank(sql)){
					sb.append("\r\n # -- 创建新表["+tableName+"]\r\n")
					.append(sql+";");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
		}
	}

	/**
	 * 修改字段
	 * @param tableName
	 * @param column
	 * @param sb
	 */
	private static void appendColumnPropSQL(String tableName,Column column,StringBuffer sb) {
		StringBuffer temp = new StringBuffer();
		boolean isPK = false;
		boolean isDefNull = false;
		
		temp.append("\r\n# -- 表字段属性变更["+tableName+"."+column.getField()+"]\r\n")
		//ALTER TABLE table_name CHANGE old_field_name new_field_name field_type;
		.append("\r\nalter table "+tableName+" modify "+column.getField()+ " "+ column.getType());
		//处理是否为空
		if(NO.equals(column.getNullStr())){
			temp.append(" "+NOT_NULL+" ");
		}else if(YES.equals(column.getNullStr())){
			temp.append(" "+DEFAULT_NULL+" ");
			isDefNull = true;
		}
		//处理主键
		if(PRI.equals(column.getKey())){
			temp.append(" PRIMARY KEY (`"+column.getField()+"`) ");
			isPK = true;
		}
		if(StringUtils.isNotBlank(column.getComment())){
			temp.append(" comment '"+column.getComment()+"'");
		}
		temp.append(";");
		if(!isDefNull){
			//该字段不能为NULL，需要进一步处理
			System.out.println("# -- 需进一步处理的\r\n"+temp.toString());
		}else{
			//字段可以为空，添加到SQL队列；
			sb.append(temp);
		}
	}
	/**
	 * 加字段
	 * @param tableName
	 * @param column
	 * @param sb
	 * @throws SQLException
	 */
	private static void appendColumnSQL(String tableName,Column column,StringBuffer sb) throws SQLException {
		sb.append("\r\n# -- 添加表字段["+tableName+"."+column.getField()+"]\r\n")
		.append("\r\nalter table "+tableName+" add "+column.getField()+" "+ column.getType()+";");
		if(StringUtils.isNotBlank(column.getComment())){
			sb.deleteCharAt(sb.length()-1);
			sb.append(" comment '"+column.getComment()+"';");
		}
	}

	public static Map<String, Table> comp(Map<String, Table> base,Map<String, Table> comped) throws Exception{
		Map<String, Table> difference = new HashMap<String, Table>();
		int diffTableCount = 0;
		int diffColumnCount = 0;
		int diffColumnPropCount = 0;
		for (Entry<String,Table> baseTableInfo : base.entrySet()) {
			String tableName = baseTableInfo.getKey();
			
			//源表（参考表）
			Table baseTable = baseTableInfo.getValue();
			//1.处理表差异
			if(comped.get(tableName)==null){
				System.out.println("[表缺失]["+tableName+"]");
				//差表，直接加入差异集
				baseTable.setNewTable(true);
				difference.put(tableName, baseTable);
				diffTableCount++;
				continue;
			}
			//比较表（修改表）
//			Table compTable = baseTableInfo.getValue();
			for (Entry<String,Column> columns : baseTable.getColunms().entrySet()) {
				
				//源表字段
				Column column = columns.getValue();
				
				Column compColumn = comped.get(tableName).getColunms().get(columns.getKey());
				if(compColumn==null){
					//2.处理字段缺失
					System.out.println("[字段缺失]表["+tableName+"] 字段["+columns.getKey()+"]");
//					Column diffCols = baseTableInfo.getValue().getColunms().get(key)
					Table diffTab = difference.get(tableName);
					if(diffTab==null){
						diffTab = new Table();
					}
					if(StringUtils.isBlank(diffTab.getName())){
						diffTab.setName(baseTable.getName());
					}
					
					Map<String,Column> cols = diffTab.getColunms();
					if(cols==null){
						cols = new HashMap<String,Column>();
					}
					column.setNewColumn(true);
					cols.put(column.getField(), column);
					diffTab.setColunms(cols);
					
					//如果修改表没有原标中的该字段，直接加入差异集
					difference.put(tableName, diffTab);
					diffColumnCount++;
					continue;
				}else{
					//3.处理字段类型，长度，注释，默认值，是否主键的差异
					if(column.getField().equals(compColumn.getField())){
						if(!StringUtils.equals(column.getComment(), compColumn.getComment())
							|| !StringUtils.equals(column.getDefaultStr(), compColumn.getDefaultStr())
							|| !StringUtils.equals(column.getExtra(), compColumn.getExtra())
							|| !StringUtils.equals(column.getKey(), compColumn.getKey())
							|| !StringUtils.equals(column.getNullStr(), compColumn.getNullStr())
							|| !StringUtils.equals(column.getType(), compColumn.getType())
								){
//							System.out.println("[字段属性不一致]表["+tableName+"] 字段["
//									+column.getField()+"] 源注释->修改表字段注释   "+column.getComment()+"->"+compColumn.getComment());
							System.out.println("[字段属性不一致]表字段["+tableName+"."+column.getField()+"] ->"+column.toString());
							System.out.println("[字段属性不一致]表字段["+tableName+"."+compColumn.getField()+"] ->"+compColumn.toString());
							Table diffTab = difference.get(tableName);
							if(diffTab==null){
								diffTab = new Table();
							}
							if(StringUtils.isBlank(diffTab.getName())){
								diffTab.setName(baseTable.getName());
							}
							Map<String,Column> cols = diffTab.getColunms();
							if(cols==null){
								cols = new HashMap<String,Column>();
							}
							column.setNewProp(true);
							cols.put(column.getField(), column);
							diffTab.setColunms(cols);
							
							//如果修改表字段属性与参考表不一致，直接加入差异集
							difference.put(tableName, diffTab);
							diffColumnPropCount++;
							continue;
						}
					}else{
						System.out.println("源表"+tableName+" 字段->"+column.getField()+" 修改表->"+compColumn.getField()+" 字段不一致");
					}
				}
			}
			
		}
		System.out.println("差表"+diffTableCount);
		System.out.println("差字段"+diffColumnCount);
		System.out.println("差字段属性"+diffColumnPropCount);
		System.out.println(JSON.toJSONString(difference));
		return difference;
	}

	 /**
	  * @Description ：xx
	  * @param       ： xx
	  * @return      ： xx
	  * @throws      ： []
	  * @author      ： lt
	 * @throws SQLException 
	  * @Date        ：2016年10月24日下午2:55:45
	  */
	private static Map<String,Table> getTablesByDB(
			Connection conn,PreparedStatement pstmt,ResultSet rs,String database)
					throws SQLException {
		Map<String, Table> tables = new HashMap<String, Table>();

		try {
			//1.填入表
			pstmt = conn.prepareStatement(String.format(selectAllTables, database));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String tableName = rs.getString(1);
				if(StringUtils.isBlank(tableName)){
					continue;
				}
				Table table = new Table();
				table.setName(tableName);
				tables.put(table.getName().toLowerCase(),table);
			}
			//2.填入表字段基本属性
			for (String tableName : tables.keySet()) {
				rs = pstmt.executeQuery(String.format(descColumns, tableName));
				while (rs.next()) {
					String field = rs.getString("Field");
					String type = rs.getString("Type");
					String nullStr = rs.getString("Null");//是否为空 YES/NO
					String key = rs.getString("Key");//主键？外键 PRI
					String defaultStr = rs.getString("Default");//默认值
					String extra = rs.getString("Extra");//自动增长
					Column column = new Column();
					column.setDefaultStr(defaultStr);
					column.setExtra(extra);
					column.setField(field);
					column.setKey(key);
					column.setNullStr(nullStr);
					column.setType(type);
//					column.setComment(comment);
					
					if(StringUtils.isBlank(tableName)){
						continue;
					}
					Table table = tables.get(tableName);
					Map<String, Column> colunms = table.getColunms();
					if(colunms==null){
						colunms = new HashMap<String, Column>();
					}
					colunms.put(column.getField(), column);
					table.setColunms(colunms);
					tables.put(tableName.toLowerCase(), table);
				}
			}
			//3.填入表字段注释
			for (String tableName : tables.keySet()) {
				rs = pstmt.executeQuery(String.format(columnsComment, tableName,database));
				while (rs.next()) {
					String filed = rs.getString(1);
					String comment = rs.getString(2);
//					System.out.println(tableName+"-->"+filed+"-->"+comment);
					tables.get(tableName).getColunms().get(filed).setComment(comment);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null){
				rs.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
		}
		return tables;
	}
	
}
