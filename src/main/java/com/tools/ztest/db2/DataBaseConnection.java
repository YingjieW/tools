package com.tools.ztest.db2;

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
public class DataBaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(Ztest.class);

    private static String driver = "com.ibm.db2.jcc.DB2Driver";
    private static String url = "jdbc:db2://172.17.106.194:50000/qa3new";
    private static String user = "db2inst";
    private static String password = "dev8132430";
    protected static Connection connection = null;
    protected static Statement statement = null;

    static {
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
