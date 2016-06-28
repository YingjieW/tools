package com.tools.ztest.database;

import com.tools.utils.ThreadSafeDateUtils;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 下午6:27
 */
public class CursorStability extends DBSetup {

    public static void main(String[] args) throws Exception {
        DBSetup.connectToMySql();
        Date startTime = new Date();
        System.out.println("CursorStability start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "select * from cs.tbl_ztest_student where id >= 1 and id <= 9 order by id with cs";

        System.out.println("First query : ");
        ResultSet resultSet = statement.executeQuery(sql);
        print(resultSet);

        Thread.sleep(20000);

        System.out.println("Second query : ");
        resultSet = statement.executeQuery(sql);
        print(resultSet);

        connection.commit();
        Date endTime =  new Date();
        System.out.println("CursorStability end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
