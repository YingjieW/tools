package com.tools.ztest.yop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.g3.sdk.yop.client.YopClient;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/18 下午3:52
 */
public class NetInQuery {

    private static final Logger logger = LoggerFactory.getLogger(NetInQuery.class);

    public static void main(String[] args) {

        String yopUrlQa = "http://10.151.30.80:18064/yop-center";
        String customerNoQa = "10040030491";
        String keyQa = "D08hwt6r413m0Pkqoj36X66r3Eb5K8lXKYP5A151R7041SkgP2Q6nfw5v8ke";
        String requestNoQa = "test1461841177887";
        String str1 = "123456789012345678901234567890123";
        String str2 = "一二三四五六七八九十";

        String bizSystem = "QFT";
        String yopUrl = "https://open.yeepay.com/yop-center";
        String requestNo = "request1463973934948";

        YopRequest request = new YopRequest(null, keyQa, yopUrl);
        request.setEncrypt(true);
        request.setSignRet(true);
        request.addParam("bizSystem", bizSystem);
        request.addParam("customerNo", customerNoQa);
        request.addParam("requestNo", requestNo);

        logger.info("####   request: {}", JSON.toJSONString(request));
        YopResponse response = YopClient.post("/rest/v1.0/merchantService/queryNetIn", request);
        logger.info("####   response: {}", JSON.toJSONString(response));

        if(response == null) {
            logger.error("*******   response is null");
        } else {
            if("SUCCESS".equals(response.getState())) {
                Map<String, String> result = JSON.parseObject(response.getStringResult(), new TypeReference<TreeMap<String, String>>() {});
                logger.info("###   result: {}", result);
            } else {
                logger.info("###   error: " + JSON.toJSONString(response.getError()));
            }
        }
    }
}
