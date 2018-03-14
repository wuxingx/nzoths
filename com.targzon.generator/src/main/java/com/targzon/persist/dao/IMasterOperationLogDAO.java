/******************************************************************************
*    @project_name   ： 天掌火锅网平台
*    @file_name      ： com.targzon.persist.dao.IMasterOperationLogDAO.java
*    @package_name   ： com.targzon.persist.dao
*    @department     ：研发部
*    @author         ：leo ma
*    (C) Copyright Chongqing Targzon Technology Co., Ltd. 2016 
*                       All Rights Reserved.
*------------------------------------------------------------------------------
*    VERSION       DATE          BY       CHANGE/COMMENT
*      1.0        2016.06      leo ma         create
*------------------------------------------------------------------------------
* *****************************************************************************
*    注意： 本内容仅限于某某软件公司内部使用，禁止转发
******************************************************************************/
package com.targzon.persist.dao;

import com.targzon.persist.dao.provider.MasterOperationLogSqlProvider;
import com.targzon.pojo.MasterOperationLog;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

public interface IMasterOperationLogDAO {
    @Delete({
        "delete from tb_erp_master_operation_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    @Insert({
        "insert into tb_erp_master_operation_log (employee_id, shop_id, ",
        "ip, create_time, ",
        "operation_type, operation_content, ",
        "mark)",
        "values (#{employeeId,jdbcType=INTEGER}, #{shopId,jdbcType=INTEGER}, ",
        "#{ip,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{operationType,jdbcType=VARCHAR}, #{operationContent,jdbcType=VARCHAR}, ",
        "#{mark,jdbcType=VARCHAR})"
    })
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insert(MasterOperationLog record);

    @InsertProvider(type=MasterOperationLogSqlProvider.class, method="insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty="id", before=false, resultType=Integer.class)
    int insertSelective(MasterOperationLog record);

    @Select({
        "select",
        "id, employee_id, shop_id, ip, create_time, operation_type, operation_content, ",
        "mark",
        "from tb_erp_master_operation_log",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @ResultMap("BaseResultMap")
    MasterOperationLog selectByPrimaryKey(Integer id);

    @UpdateProvider(type=MasterOperationLogSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MasterOperationLog record);

    @Update({
        "update tb_erp_master_operation_log",
        "set employee_id = #{employeeId,jdbcType=INTEGER},",
          "shop_id = #{shopId,jdbcType=INTEGER},",
          "ip = #{ip,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "operation_type = #{operationType,jdbcType=VARCHAR},",
          "operation_content = #{operationContent,jdbcType=VARCHAR},",
          "mark = #{mark,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MasterOperationLog record);
}