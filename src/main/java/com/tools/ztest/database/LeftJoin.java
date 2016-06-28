package com.tools.ztest.database;

import com.tools.utils.ThreadSafeDateUtils;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/28 下午3:13
 */
public class LeftJoin extends DBSetup {

    public static void main(String[] args) throws Exception {

        smallJoinLargeQuery();
        largeJoinSmall();

//        statement.close();
//        connection.close();
    }

    private static void smallJoinLargeQuery() throws Exception {
        Date startTime = new Date();
        System.out.println("smallJoinLarge start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
//        String smallJoinLargeSql = "select s.* from cs.tbl_ztest_student s left join cs.tbl_process_trxorder_16q1 t on s.id = t.id where t.id is null with ur";
        String smallJoinLargeSql = "select s.* from cs.tbl_ztest_student s left join cs.tbl_process_trxorder_16q1 t on s.id = t.id with ur";

        System.out.println("smallJoinLarge query : ");
        ResultSet resultSet = statement.executeQuery(smallJoinLargeSql);
//        print(resultSet);

        connection.commit();
        Date endTime =  new Date();
        System.out.println("smallJoinLarge end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");
    }

    private static void largeJoinSmall() throws Exception {
        Date startTime = new Date();
        System.out.println("largeJoinSmall start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
//        String largeJoinSmallSql = "select s.* from cs.tbl_process_trxorder_16q1 t left join cs.tbl_ztest_student s on s.id = t.id where s.id is null with ur";
        String largeJoinSmallSql = "select s.* from cs.tbl_process_trxorder_16q1 t left join cs.tbl_ztest_student s on s.id = t.id with ur";

        System.out.println("largeJoinSmall query : ");
        ResultSet resultSet = statement.executeQuery(largeJoinSmallSql);
//        print(resultSet);

        connection.commit();
        Date endTime =  new Date();
        System.out.println("largeJoinSmall end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");
    }
}
