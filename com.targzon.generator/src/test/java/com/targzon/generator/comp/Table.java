/******************************************************************************
*    @project_name   ： 天掌火锅网平台
*    @file_name      ： Tables.java
*    @package_name   ： com.targzon.generator.comp
*    @department     ： 研发部
*    @author         ： lt
*    (C) Copyright Chongqing Targzon Technology Co., Ltd. 2016 
*                       All Rights Reserved.
*------------------------------------------------------------------------------
*    VERSION       DATE             BY       CHANGE/COMMENT
*      1.0        2016年10月24日        		lt         下午3:55:33
*------------------------------------------------------------------------------
* *****************************************************************************
*    注意： 本内容仅限于天掌科技软件公司内部使用，禁止转发
******************************************************************************/
package com.targzon.generator.comp;

import java.util.Map;

/**
 * @author cqtoo
 *
 */
public class Table {

	private String name;
	private Map<String,Column> colunms;
	private boolean isNewTable;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, Column> getColunms() {
		return colunms;
	}
	public void setColunms(Map<String, Column> colunms) {
		this.colunms = colunms;
	}
	public boolean isNewTable() {
		return isNewTable;
	}
	public void setNewTable(boolean isNewTable) {
		this.isNewTable = isNewTable;
	}
	
}
