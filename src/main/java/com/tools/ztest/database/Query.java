package com.tools.ztest.database;

import com.tools.util.ThreadSafeDateUtils;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/6/28 下午6:23
 */
public class Query extends DBSetup {

    public static void main(String[] args) throws Exception {
        Date startTime = new Date();
        System.out.println("Query start time: " + ThreadSafeDateUtils.formatDateTimeMillis(startTime));
        String sql = "select * from yqtaccounting.tbl_settlement_accounting where 1=1";

        ResultSet resultSet = statement.executeQuery(sql);
        print(resultSet);

        connection.commit();
        Date endTime =  new Date();
        System.out.println("Query end time: " + ThreadSafeDateUtils.formatDateTimeMillis(endTime)
                + ", cost: " + (endTime.getTime() - startTime.getTime()) + " millis");

        statement.close();
        connection.close();
    }
}
