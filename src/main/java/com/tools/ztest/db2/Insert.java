package com.tools.ztest.db2;

import com.tools.utils.ThreadSafeDateUtils;

import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/23 上午11:48
 */
public class Insert extends DataBaseConnection {

    public static void main(String[] args) throws Exception {
        Date startTime = new Date();
        System.out.println("insert start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "insert into cs.tbl_ztest_student(id, name, score) values(7, 'Seven', 99)";
        statement.execute(sql);

//        Thread.sleep(20000);
//        connection.rollback();
        connection.commit();
        Date endTime = new Date();
        System.out.println("insert end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
