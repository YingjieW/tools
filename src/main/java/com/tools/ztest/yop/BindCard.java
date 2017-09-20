package com.tools.ztest.yop;

import com.yeepay.g3.sdk.yop.client.YopClient;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/27 下午3:59
 */
public class BindCard {

    static String serverUrl = "http://10.151.30.80:18064/yop-center/";
    static String appkey = "B112345678901239";

    static String AppSecret = "9jxLvTH2BO+0WylEWTSBlQ==";
    //    static String AppSecret = "Vmt/sFQgWbJ6b4uKWLlFGw==";
    static String merchantNo = "B112345678901239";

    public static void main(String[] args) throws Exception {
        bindCard();
    }

    public static void bindCard() {
        YopRequest request = new YopRequest(appkey, AppSecret, serverUrl);
        String merchantUserId = "test03218";
        String requestNo = System.currentTimeMillis() + "";
        String authRecordTypeEnum = "BIND";
        String webCallbackUrl = "http://www.baidu.com";
        String serviceCallbackUrl = "http://www.baidu.com";
        String returnUrl = "http://www.baidu.com";
        request.setEncrypt(true);
        request.setSignRet(true);
        request.addParam("merchantNo", merchantNo);
        request.addParam("merchantUserId", merchantUserId);
        request.addParam("requestNo", requestNo);
        request.addParam("authRecordTypeEnum", authRecordTypeEnum);

        request.addParam("webCallbackUrl", webCallbackUrl);
        request.addParam("serviceCallbackUrl", serviceCallbackUrl);
        request.addParam("returnUrl", returnUrl);
        request.addParam("templateType", "APP");

        request.addParam("clientSourceEnum", "MOBILE");

        request.setSignAlg("sha-256");
        System.out.println(request.toQueryString());
//        YopResponse response = YopClient.post("/rest/v1.0/payplus/user/bindCard", request);
        YopResponse response = YopClient.post("/rest/v1.0/user/bindCard", request);

        System.out.println("绑卡用例执行结果" + response.toString());
    }
}
