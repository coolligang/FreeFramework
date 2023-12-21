package com.lg.tool.mysql;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

// MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
//static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";

// MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
//static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
//static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

public class MysqlService {
    private static Logger logger = LogManager.getLogger(MysqlService.class);
    private Connection conn;
    private Statement stmt;

    /**
     * MysqlService 构造方法
     *
     * @param host     ip:port
     * @param db       database name
     * @param user     database's login name name
     * @param password databases's login password
     */
    public MysqlService(String host, String db, String user, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connStr = String.format("jdbc:mysql://%s/%s?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC", host, db);
            this.conn = DriverManager.getConnection(connStr, user, password);
            this.stmt = this.conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Connect database or create statement failed.\n" + e.getMessage());
        }
    }

    // 关闭连接
    public void close() {
        try {
            if (this.stmt != null) {
                stmt.close();
            }
            if (this.conn != null) {
                this.conn.close();
            }
        } catch (SQLException e) {
            logger.warn("mysql close error. " + e.getMessage());
        }
    }

    public int insert(String sqlStr) {

        return -1;
    }

    public ResultSet select(String sqlStr) {
        // 执行查询
        ResultSet rs = null;
        try {
            rs = this.stmt.executeQuery(sqlStr);
        } catch (SQLException e) {
            logger.error("select error. " + e.getMessage());
        }
        return rs;
        // 读 rs 的结果
//        while(rs.next()){
//            // 通过字段检索
//            int id  = rs.getInt("id");
//            String name = rs.getString("name");
//            String url = rs.getString("url");
//
//            // 输出数据
//            System.out.print("ID: " + id);
//            System.out.print(", 站点名称: " + name);
//            System.out.print(", 站点 URL: " + url);
//            System.out.print("\n");
//        }
//        rs.close();
    }

    public static void main(String[] args) {

    }
}
