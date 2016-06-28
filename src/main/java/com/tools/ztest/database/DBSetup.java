package com.tools.ztest.database;

import com.tools.ztest.Ztest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 上午11:01
 */
public class DBSetup {

    private static final Logger logger = LoggerFactory.getLogger(Ztest.class);

    protected static Connection connection = null;
    protected static Statement statement = null;
    private static String currentDatabase = "";

    /**
     * 设置默认链接的数据库
     */
    static {
//        connectToDB2();
        connectToMySql();
    }

    /**
     * connect to db2.
     */
    public static void connectToDB2() {
        String driver = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://172.17.106.194:50000/qa3new";
        String user = "db2inst";
        String password = "dev8132430";
        setConnection(driver, url, user, password);
        currentDatabase = "DB2";
    }

    /**
     * connect to mysql.
     */
    public static void connectToMySql() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String user = "root";
        String password = "admin";
        setConnection(driver, url, user, password);
        currentDatabase = "MYSQL";
    }

    public static String getCurrentDatabase() {
        return currentDatabase;
    }

    /**
     * setup connection.
     * @param driver
     * @param url
     * @param user
     * @param password
     */
    private static void setConnection(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
            statement = connection.createStatement();
        } catch (Throwable t) {
            logger.error("basic setup exception ", t);
        }
    }

    /**
     * 输出表tbl_ztest_student
     * @param resultSet
     * @throws Exception
     */
    public static void print(ResultSet resultSet) throws Exception {
        while (resultSet.next()) {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            int score = resultSet.getInt(3);
            System.out.println("id: " + id + ",\t name: " + name + ",\t\t score: " + score);
        }
    }
}
