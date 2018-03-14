/******************************************************************************
*    @project_name   ： 天掌火锅网平台
*    @file_name      ： com.targzon.persist.dao.provider.MasterOperationLogSqlProvider.java
*    @package_name   ： com.targzon.persist.dao.provider
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
package com.targzon.persist.dao.provider;

import static org.apache.ibatis.jdbc.SqlBuilder.BEGIN;
import static org.apache.ibatis.jdbc.SqlBuilder.INSERT_INTO;
import static org.apache.ibatis.jdbc.SqlBuilder.SET;
import static org.apache.ibatis.jdbc.SqlBuilder.SQL;
import static org.apache.ibatis.jdbc.SqlBuilder.UPDATE;
import static org.apache.ibatis.jdbc.SqlBuilder.VALUES;
import static org.apache.ibatis.jdbc.SqlBuilder.WHERE;

import com.targzon.pojo.MasterOperationLog;

public class MasterOperationLogSqlProvider {

    public String insertSelective(MasterOperationLog record) {
        BEGIN();
        INSERT_INTO("tb_erp_master_operation_log");
        
        if (record.getEmployeeId() != null) {
            VALUES("employee_id", "#{employeeId,jdbcType=INTEGER}");
        }
        
        if (record.getShopId() != null) {
            VALUES("shop_id", "#{shopId,jdbcType=INTEGER}");
        }
        
        if (record.getIp() != null) {
            VALUES("ip", "#{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOperationType() != null) {
            VALUES("operation_type", "#{operationType,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationContent() != null) {
            VALUES("operation_content", "#{operationContent,jdbcType=VARCHAR}");
        }
        
        if (record.getMark() != null) {
            VALUES("mark", "#{mark,jdbcType=VARCHAR}");
        }
        
        return SQL();
    }

    public String updateByPrimaryKeySelective(MasterOperationLog record) {
        BEGIN();
        UPDATE("tb_erp_master_operation_log");
        
        if (record.getEmployeeId() != null) {
            SET("employee_id = #{employeeId,jdbcType=INTEGER}");
        }
        
        if (record.getShopId() != null) {
            SET("shop_id = #{shopId,jdbcType=INTEGER}");
        }
        
        if (record.getIp() != null) {
            SET("ip = #{ip,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        if (record.getOperationType() != null) {
            SET("operation_type = #{operationType,jdbcType=VARCHAR}");
        }
        
        if (record.getOperationContent() != null) {
            SET("operation_content = #{operationContent,jdbcType=VARCHAR}");
        }
        
        if (record.getMark() != null) {
            SET("mark = #{mark,jdbcType=VARCHAR}");
        }
        
        WHERE("id = #{id,jdbcType=INTEGER}");
        
        return SQL();
    }
}