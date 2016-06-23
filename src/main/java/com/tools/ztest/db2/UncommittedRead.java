package com.tools.ztest.db2;

import com.tools.utils.ThreadSafeDateUtils;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 下午5:37
 */
public class UncommittedRead extends DataBaseConnection {

    public static void main(String[] args) throws Exception {
        Date startTime = new Date();
        System.out.println("UncommittedRead start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "select * from cs.tbl_ztest_student where id >= 1 and id <= 9 order by id with ur";

        System.out.println("First read: ");
        ResultSet resultSet = statement.executeQuery(sql);
        print(resultSet);

        Thread.sleep(20000);

        System.out.println("Second read: ");
        resultSet = statement.executeQuery(sql);
        print(resultSet);

        connection.commit();
        Date endTime =  new Date();
        System.out.println("UncommittedRead end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                    + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
