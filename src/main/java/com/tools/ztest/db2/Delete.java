package com.tools.ztest.db2;

import com.tools.utils.ThreadSafeDateUtils;

import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 上午10:28
 */
public class Delete extends DataBaseConnection {

    public static void main(String[] args) throws Exception {
        Date startTime = new Date();
        System.out.println("delete start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "delete from cs.tbl_ztest_student where id = 7";
        statement.execute(sql);

//        Thread.sleep(20000);
        connection.commit();
        Date endTime = new Date();
        System.out.println("delete end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
