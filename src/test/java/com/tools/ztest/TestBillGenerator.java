package com.tools.ztest;

import com.alibaba.fastjson.JSON;
import com.tools.BaseTest;
import com.yeepay.utils.jdbc.dal.DALDataSource;
import open.framework.bill.common.CommonConstants;
import open.framework.bill.config.BillConfig;
import open.framework.bill.config.ColumnValueConfig;
import open.framework.bill.database.type.ColumnType;
import open.framework.bill.enums.BillCharsetEnum;
import open.framework.bill.enums.DatabaseTypeEnum;
import open.framework.bill.generator.BillGenerator;
import open.framework.bill.pojo.BillStatistics;
import org.junit.Before;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/29 下午4:56
 */
public class TestBillGenerator extends BaseTest {

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testDb2Generate() throws Exception {
        DataSource dataSource = beanFactory.getBean(DALDataSource.class);
        BillConfig billConfig = new BillConfig();
        billConfig.setDataSource(dataSource);
        billConfig.setDatabaseType(DatabaseTypeEnum.DB2);
        billConfig.setBillCharset(BillCharsetEnum.UTF8);
        billConfig.setLeftSeparator("`");
        billConfig.setRightSeparator(",");
//        billConfig.setEmptyFileMark("no data today...");
        billConfig.setBillHeadTitle("交易时间,订单号,商户请求号,商户编号,交易类型,交易金额（元）,交易手续费,退款金额（元),退回手续费,原交易请求号,支付方式,设备编码,凭证号,备注");
        billConfig.setBillTailTitle("交易总笔数,交易总金额,交易总手续费");
        billConfig.setBillFilePath("/Users/YJ/shared/1020006110000615_trade_bill_generator.csv");
        billConfig.setSql("SELECT fund.ORDER_TIME,fund.FUND_EXTERNAL_NO,fund.REQUEST_NO,fund.PP_MERCHANT_NO, 'TRADE' as TRADE_TYPE, fund.AMOUNT, fund.FEE, " +
                "'' as REFUND_AMOUNT, '' AS REFUND_FEE, '' AS SOURCE_REQUEST_NO, fund.PAY_TOOL, fund.DEVICE_NO, fund.VOUCHER_NO, fund.remark\n" +
                "FROM TBL_FUND_ACCOUNTING fund\n" +
                "WHERE fund.CHECK_FAIL_FLAG IN ('0','2','4')\n" +
                "and fund.PP_MERCHANT_NO = ?\n" +
                "and fund.CHECK_DATE >= ?\n" +
                "and fund.CHECK_DATE <= ?\n" +
                "with ur");
        // columnPatternConfigMap
        LinkedHashMap<String, ColumnValueConfig> configLinkedHashMap = new LinkedHashMap<>();
        configLinkedHashMap.put("ORDER_TIME", new ColumnValueConfig("yyyy-MM-dd HH:mm:ss"));
        configLinkedHashMap.put("AMOUNT", new ColumnValueConfig("0.00"));
        configLinkedHashMap.put("FEE", new ColumnValueConfig("0.00"));
        billConfig.setColumnPatternConfigMap(configLinkedHashMap);
        // tailColumnsNeedStatistic
        LinkedHashMap<String, ColumnType> tailColumnsNeedStatistic = new LinkedHashMap<>();
        tailColumnsNeedStatistic.put(CommonConstants.TOTAL_COUNT_MARK, ColumnType.LONG);
        tailColumnsNeedStatistic.put("AMOUNT", ColumnType.DECIMAL);
        tailColumnsNeedStatistic.put("FEE", ColumnType.DECIMAL);
        billConfig.setTailColumnsNeedStatistic(tailColumnsNeedStatistic);
        // sqlParams
        Object[] params = new Object[] {"1020006110000615", "2017-07-12", "2017-07-12"};
        billConfig.setSqlParams(params);

        String[] merchantNos = {"0000000110000305", "Test002", "0000000110000378","1020006110000614","1020006110000615",
                "1111111110000216","Test001", "1111111110000222","1111111110000229"};
        BillStatistics billStatistics = new BillStatistics();
        for (int i = 0; i < merchantNos.length; i++) {
            params[0] = merchantNos[i];
            if (i == merchantNos.length - 1) {
                billStatistics.setLast(true);
            }
            BillGenerator.generateExt(billConfig, billStatistics);
            System.out.println("===> billStatistics: " + JSON.toJSONString(billStatistics));
        }

//        BillGenerator.generate(billConfig, true);
        System.out.println("=======================> SUCCESS.....");
    }

    @Test
    public void testMySqlGenerator() throws Exception {
        DataSource dataSource = beanFactory.getBean(DataSource.class);
        BillConfig billConfig = new BillConfig();
        billConfig.setDataSource(dataSource);
        billConfig.setDatabaseType(DatabaseTypeEnum.MYSQL);
        billConfig.setBillCharset(BillCharsetEnum.UTF8);
        billConfig.setLeftSeparator("`");
        billConfig.setRightSeparator(",");
        billConfig.setEmptyFileMark("no data today...");
        billConfig.setBillHeadTitle("交易时间,完成时间,交易金额（元）,交易手续费,商户编号,银行卡号, 卡类型, 身份证号");
        billConfig.setBillTailTitle("交易总笔数,交易总金额,交易总手续费");
        billConfig.setBillFilePath("/Users/YJ/Documents/generator/mysql_bill_2018_01_31.csv");
        billConfig.setSql("select order_time, completed_time, amount, fee, merchant_no,bank_card_no, card_type, id_card\n" +
                "from paybar.tbl_pay_accounting p\n" +
                "where p.merchant_no = ?\n " +
                "and p.CREATE_TIME >= ?\n" +
                "and p.CREATE_TIME <= ?");
        // columnPatternConfigMap
        LinkedHashMap<String, ColumnValueConfig> configLinkedHashMap = new LinkedHashMap<>();
        configLinkedHashMap.put("ORDER_TIME", new ColumnValueConfig("yyyy-MM-dd HH:mm:ss"));
        configLinkedHashMap.put("COMPLETED_TIME", new ColumnValueConfig("yyyy-MM-dd HH:mm:ss"));
        configLinkedHashMap.put("AMOUNT", new ColumnValueConfig("0.00"));
        configLinkedHashMap.put("FEE", new ColumnValueConfig("0.00", "尚未计费"));
        billConfig.setColumnPatternConfigMap(configLinkedHashMap);
        // tailColumnsNeedStatistic
        LinkedHashMap<String, ColumnType> tailColumnsNeedStatistic = new LinkedHashMap<>();
        tailColumnsNeedStatistic.put(CommonConstants.TOTAL_COUNT_MARK, ColumnType.LONG);
        tailColumnsNeedStatistic.put("AMOUNT", ColumnType.DECIMAL);
        tailColumnsNeedStatistic.put("FEE", ColumnType.DECIMAL);
        billConfig.setTailColumnsNeedStatistic(tailColumnsNeedStatistic);
        // sqlParams
        Object[] params = new Object[] {"", "2018-01-30 00:00:00", "2018-01-30 23:59:59"};
        billConfig.setSqlParams(params);

        String[] merchantNos = {"1318012823236834", "1318012823236835", "1318012823236836", "1318012823236837", "1318012823236838"};
        BillStatistics billStatistics = new BillStatistics();
        for (int i = 0; i < merchantNos.length; i++) {
            params[0] = merchantNos[i];
            billStatistics.setLast(i == (merchantNos.length - 1));
            BillGenerator.generateExt(billConfig, billStatistics);
            System.out.println("####### billStatistics: " + JSON.toJSONString(billStatistics));
        }
    }
}
