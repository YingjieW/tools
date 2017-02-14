package com.tools.ztest.database;

import com.tools.util.ThreadSafeDateUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/7 下午5:04
 */
public class UpdateRepeatly{

    public static void main(String[] args) throws Exception {
        Connection connection = null;
        Statement statement = null;
        String driver = "com.ibm.db2.jcc.DB2Driver";
        String url = "jdbc:db2://172.17.106.194:50000/qa3new";
        String user = "db2inst";
        String password = "dev8132430";
        Class.forName(driver);
        connection = DriverManager.getConnection(url, user, password);
        connection.setAutoCommit(false);
        statement = connection.createStatement();


        Date startTime = new Date();
        System.out.println("UpdateRepeatly start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "update cs.tbl_ztest_student set score = score + 10 where score = 100";
        statement.execute(sql);

        String queryWithUr = "select * from cs.tbl_ztest_student where id = 1 with ur";
        String queryWithoutUr = "select * from cs.tbl_ztest_student where id = 1";

        ResultSet resultSetWithUr = statement.executeQuery(queryWithUr);
        System.out.println("1 - Read with ur: ");
        print(resultSetWithUr);

        ResultSet resultSetWithoutUr = statement.executeQuery(queryWithoutUr);
        System.out.println("1 - Read without ur: ");
        print(resultSetWithoutUr);

        Thread.sleep(30000);
//        connection.rollback();
        connection.commit();

        resultSetWithUr = statement.executeQuery(queryWithUr);
        System.out.println("2 - Read with ur: ");
        print(resultSetWithUr);

        resultSetWithoutUr = statement.executeQuery(queryWithoutUr);
        System.out.println("2 - Read without ur: ");
        print(resultSetWithoutUr);

        Date endTime = new Date();
        System.out.println("UpdateRepeatly end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

//        statement.close();
//        connection.close();
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
