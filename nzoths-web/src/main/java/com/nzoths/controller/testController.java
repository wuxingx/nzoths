package com.nzoths.controller;

import com.nzoths.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.*;
import java.util.Date;

@RestController("/")
public class testController {

    @Autowired
    private ITestService testService;


    @RequestMapping("/now")
    String hehe() {
        return "现在时间：" + (new Date()).toLocaleString();
    }

    @RequestMapping("getTest")
    public String getTest(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String passowrd
    ) {
        testService.insert(name,passowrd);
        return "1";
    }

    @RequestMapping("/jdbc")
    String jdbc() {
        Integer all = this.getAll();
        return all + "";
    }
    private static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://www.wuxingx.top:3306/nzoths";
        String username = "root";
        String password = "zx377469484Z_X";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = (Connection) DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void main(String args[]) {
        int num = 2147483647 ;
        long temp = num + 2L ;
        System.out.println(Math.round(-15.61)) ;
    }

    private static Integer getAll() {
        Connection conn = getConn();
        String sql = "select * from tb_user";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement)conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            int col = rs.getMetaData().getColumnCount();
            System.out.println("============================");
            while (rs.next()) {
                for (int i = 1; i <= col; i++) {
                    System.out.print(rs.getString(i) + "\t");
                    if ((i == 2) && (rs.getString(i).length() < 8)) {
                        System.out.print("\t");
                    }
                }
                System.out.println("");
            }
            System.out.println("============================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
