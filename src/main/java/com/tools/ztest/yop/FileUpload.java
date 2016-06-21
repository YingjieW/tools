package com.tools.ztest.yop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yeepay.g3.sdk.yop.client.YopClient;
import com.yeepay.g3.sdk.yop.client.YopRequest;
import com.yeepay.g3.sdk.yop.client.YopResponse;
import com.yeepay.g3.utils.common.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/5/5 上午11:02
 */
public class FileUpload {

    public static void main(String[] args) throws Exception {

        String secretKey_qa = "7YT2549M7EpL8362al1CrNDE9i9500oQP0lp4Pt060beRJ5i90s483DX3530";
        String url_qa = "http://10.151.30.88:8064/yop-center";
        String customerNo_qa = "10040007707";

        String secretKey_appKey = "NWNZQslh36xJP2a5rodX1Q==";

        //        TrustAllHttpsCertificates.setTrue();

        String secretKey = "ER59202j4EqI44c18L76iyjVjTl17c75268w15L9M5f7638A8l2zJ7UB0d54";
        String url = "https://open.yeepay.com/yop-center";
        String customerNo = "10012442799";

        YopRequest request = new YopRequest(null, secretKey, url);
        request.setEncrypt(true);
        request.setSignRet(true);
        request.addParam("customerNo", customerNo);
        request.addParam("fileType","IMAGE");
        request.addParam("fileURI", "file:/Users/YJ/Downloads/test.png");
        YopResponse response = YopClient.upload("/rest/v1.0/file/upload", request);

        System.out.println();
        System.out.println("#######   request: " + JSON.toJSONString(request));
        System.out.println("#######   response: " + JSON.toJSONString(response));

        if(response.isSuccess()) {
            Map<String, String> stringResult = JSON.parseObject(response.getStringResult(), new TypeReference<HashMap<String, String>>() {});
            List<HashMap<String, String>> fileUploadInfoList = JSON.parseObject(stringResult.get("files"), new TypeReference<List<HashMap<String, String>>>() {});
            if(CollectionUtils.isEmpty(fileUploadInfoList) || fileUploadInfoList.get(0) == null || fileUploadInfoList.get(0).size() == 0) {
                throw new RuntimeException(" 文件上传异常");
            }
            String fileLocation = fileUploadInfoList.get(0).get("fileLocation");
            System.out.println("#######   fileLocation : " + fileLocation);
        } else {
            throw new RuntimeException(" 文件上传异常");
        }
    }
}
