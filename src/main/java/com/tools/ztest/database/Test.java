package com.tools.ztest.database;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/31 上午10:55
 */
public class Test extends DBSetup{

    public static void main(String[] args) throws Exception {
        Statement statement = connection.createStatement();
        try {
            String sql = "select * from test.tbl_ztest_student where id >= 1 and id <= 9 order by id";
            ResultSet resultSet = statement.executeQuery(sql);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            statement.close();
        }
    }
}
