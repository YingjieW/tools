package com.tools.ztest.yop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tools.ztest.yop.entity.YeepayProductEntity;
import com.yeepay.g3.sdk.yop.client.YopClient;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/18 下午5:29
 */
public class ProductOpen {

    private static final Logger logger = LoggerFactory.getLogger(ProductOpen.class);

    public static void main(String[] args) {
        String key = "D08hwt6r413m0Pkqoj36X66r3Eb5K8lXKYP5A151R7041SkgP2Q6nfw5v8ke";
        String yopUrl = "http://10.151.30.80:18064/yop-center";

        String bizSystem = "QFT";
        String customerNo = "10040030491";
        String requestNo = "request1463023178123";
        String subCustomerNo = "10040030666";
        String merchantCategory = "";
        String cashDepositType = "DELAYMONEY";
        String cashDepositAmount = "123.45";
        String invoice = "0";
        String invoiceDetail = "我要开发票，我是发票详细信息（备注）- Test";
        String businessContent = "我是商户经营内容 - Test";
        String merchantMemo = "我是商户备注 - Test";
        String businessUrl = "http://www.baidu.com";
        String isSubmit = "0";

        Map<String, String> fileLocationDetailMap = new HashMap<String, String>();
        fileLocationDetailMap.put("CORPCODE", "http://172.17.106.197:8081/ucm/201605/6491aede549145be9fcc845e44753b2f.test.jpg");
        fileLocationDetailMap.put("IDCARD_FRONT", "http://172.17.106.197:8081/ucm/201605/e23c7bc5e4e14653843524a4e6cf922d.test.jpg");
        fileLocationDetailMap.put("IDCARD_BACK", "http://172.17.106.197:8081/ucm/201605/2ae6e405dc4f4d0db54bb0f15317ab98.test.jpg");
        fileLocationDetailMap.put("OPBANKCODE", "http://172.17.106.197:8081/ucm/201605/e23c7bc5e4e14653843524a4e6cf922d.test.jpg");
        String fileLocationDetail = JSON.toJSONString(fileLocationDetailMap);

        YeepayProductEntity y1 = new YeepayProductEntity();
        y1.setYeepayProductName("PC_B2C");
        y1.setFeeRateChargeMethod("PAY_FEE");
        y1.setFeeRate(new BigDecimal("0.001"));
        YeepayProductEntity y2 = new YeepayProductEntity();
        y2.setYeepayProductName("PC_B2B");
        y2.setFeeRateChargeMethod("PAY_FEE");
        y2.setFeeRate(new BigDecimal("0.002"));
        YeepayProductEntity y3 = new YeepayProductEntity();
        y3.setYeepayProductName("PC_ONEKEY");
        y3.setFeeRateChargeMethod("PAY_FEE");
        y3.setFeeRate(new BigDecimal("0.003"));
        YeepayProductEntity y4 = new YeepayProductEntity();
        y4.setYeepayProductName("ONEKEY_WAP");
        y4.setFeeRateChargeMethod("PAY_FEE");
        y4.setFeeRate(new BigDecimal("0.004"));
        YeepayProductEntity y5 = new YeepayProductEntity();
        y5.setYeepayProductName("WECHAT_PC");
        y5.setFeeRateChargeMethod("PAY_FEE");
        y5.setFeeRate(new BigDecimal("0.005"));
        List<YeepayProductEntity> yeepayProductEntityList = new ArrayList<YeepayProductEntity>();
        yeepayProductEntityList.add(y1);
        yeepayProductEntityList.add(y2);
        yeepayProductEntityList.add(y3);
        yeepayProductEntityList.add(y4);
        yeepayProductEntityList.add(y5);
        String yeepayProductDetail = JSON.toJSONString(yeepayProductEntityList);

        YopRequest request = new YopRequest(null, key, yopUrl);
        request.setEncrypt(true);
        request.setSignRet(true);
        request.addParam("bizSystem", bizSystem);
        request.addParam("customerNo", customerNo);
        request.addParam("requestNo", requestNo);
        request.addParam("subCustomerNo", subCustomerNo);
        request.addParam("merchantCategory", merchantCategory);
        request.addParam("cashDepositType", cashDepositType);
        request.addParam("cashDepositAmount", cashDepositAmount);
        request.addParam("yeepayProductDetail", yeepayProductDetail);
        request.addParam("invoice", invoice);
        request.addParam("invoiceDetail", invoiceDetail);
        request.addParam("businessContent", businessContent);
        request.addParam("merchantMemo", merchantMemo);
        request.addParam("businessUrl", businessUrl);
        request.addParam("isSubmit", isSubmit);
        request.addParam("fileLocationDetail", fileLocationDetail);

        logger.info("#######   request: {}", JSON.toJSONString(request));

        YopResponse response = YopClient.post("/rest/v1.0/merchantService/productOpen", request);

        logger.info("#######   response: {}",JSON.toJSONString(response));

        if(response == null) {
            logger.error("*******   response is null");
        } else {
            if("SUCCESS".equals(response.getState())) {
                Map<String, String> result = JSON.parseObject(response.getStringResult(), new TypeReference<TreeMap<String, String>>() {});
                logger.info("#######   result: {}", result);
            } else {
                logger.info("#######   error: " + JSON.toJSONString(response.getError()));
            }
        }
    }
}
