package com.tools.ztest.database;

import com.tools.utils.ThreadSafeDateUtils;

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
        String sql = "update cs.tbl_ztest_student set score = 93 where id = 1";
        statement.execute(sql);

//        Thread.sleep(20000);
//        connection.rollback();
        connection.commit();
        Date endTime = new Date();
        System.out.println("update end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
