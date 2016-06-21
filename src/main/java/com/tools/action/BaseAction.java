package com.tools.action;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class BaseAction {

    private static final Logger logger = LoggerFactory.getLogger(BaseAction.class);
   
    /**
     * ajax 请求返回
     * 
     * @param response
     * @param success
     *            结果状态
     * @param message
     *            详情信息
     */
    protected void writeResponse(HttpServletResponse response, boolean success, String message) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("message", message);
        writeResponse(response, result);
    }

    /**
     * ajax 请求返回
     * 
     * @param response
     * @param result
     */
    protected void writeResponse(HttpServletResponse response, Map<String, Object> result) {

        try {
            String json = JSONObject.toJSONString(result);
            response.getWriter().print(json);
        } catch (IOException e) {
            logger.error("Object2JSON 转换异常", e);
        }
    }
   
    /**
     * 获取request请求参数
     * 
     * @param request
     * @return
     */
    protected Map<String, String> getReqeustParams(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Enumeration<?> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            if (paramValues.length == 1) {
                String paramValue = paramValues[0];
                if (paramValue.length() != 0) {
                    params.put(paramName, paramValue);
                }
            }
        }
        return params;
    }
}
