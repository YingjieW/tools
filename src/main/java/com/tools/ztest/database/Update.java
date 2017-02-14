package com.tools.ztest.database;

import com.tools.util.ThreadSafeDateUtils;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 下午12:23
 */
public class Update extends DBSetup {

    public static void main(String[] args) throws Exception {
        Date startTime = new Date();
        System.out.println("update start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "update cs.tbl_ztest_student set score = score + 1 where score = 100";
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
        System.out.println("update end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

//        statement.close();
//        connection.close();
    }
}
