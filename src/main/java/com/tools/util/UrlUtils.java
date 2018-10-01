package com.tools.util;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.rpc.RpcContext;
import com.tools.constant.SessionConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/29 下午5:21
 */
public class UrlUtils {

    private static final Logger logger = LoggerFactory.getLogger(UrlUtils.class);

    /**
     * 格式化请求contentType
     *
     * @return
     */
    public static String formartContentType(String type) {
        String substring = type.substring(type.length() - 4, type.length());
        if (!substring.startsWith(".")) {
            substring = type.substring(type.length() - 3, type.length());
        }
        if (".css".equals(substring)) {
            return "text/css";
        } else if (".png".equals(substring)) {
            return "image/png";
        } else if (".js".equals(substring)) {
            return "application/x-javascript";
        } else if (".jpg".equals(substring)) {
            return "image/jpeg";
        } else if (".gif".equals(substring)) {
            return "image/gif";
        } else {
            return "text/txt";
        }
    }

    /**
     * 判断当前路径是否为ajax路径
     *
     * @param reqest
     * @return
     */
    public static boolean isAjaxRequest(HttpServletRequest reqest) {
        // 请求头
        String reqHeader = reqest.getHeader("X-Requested-With");
        // ajax请求,返回true
        return reqHeader != null && reqHeader.equalsIgnoreCase("XMLHttpRequest");
    }

    /**
     * 从request中获得参数Map，并返回可读的Map
     *
     * @param request
     * @return
     */
    public static Map getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map properties = request.getParameterMap();
        // 返回值Map
        Map returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name = "";
        String value = "";
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else if (valueObj instanceof String[]) {
                String[] values = (String[]) valueObj;
                for (int i = 0; i < values.length; i++) {
                    value = values[i] + ",";
                }
                value = value.substring(0, value.length() - 1);
            } else {
                value = valueObj.toString();
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }

    /**
     * 得到请求参数表达式
     *
     * @param paramMap
     * @return
     */
    public static String getRequestParamStr(Map paramMap) {
        Set keySet = paramMap.keySet();
        StringBuffer params = new StringBuffer();
        params.append("?");
        for (Object obj : keySet) {
            params.append(obj.toString());
            params.append("=");
            StringBuffer values = new StringBuffer();
            Object obj2 = paramMap.get(obj);
            if (null == obj2) {
                values.append("");
            } else if (obj2 instanceof String[]) {
                String[] oo = (String[]) obj2;
                for (String string : oo) {
                    values.append(string);
                }
            } else if (obj2 instanceof Object[]) {
                Object[] obj3 = (Object[]) obj2;
                for (Object string : obj3) {
                    values.append(string.toString());
                }
            }
            params.append(values.toString());
            params.append("&");
        }
        return params.toString().substring(0, params.toString().length() - 1);
    }

    /**
     * 获取用户的真实IP
     *
     * @return
     */
    public static String getIpAddr(HttpServletRequest req) {

        if (req == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("REQUEST IS NULL FOR GETIP");
            }
            RpcContext context = RpcContext.getContext();
            if (context != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("REQUEST IS {} from dubbo ", context.getRemoteHost());
                }
                // 非rmi调用，尝试从dubbo的上下文中获取调用方ip
                return context.getRemoteHost();
            }
            return null;
        }

        String ip = req.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = req.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP地址，多个IP由","分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

    /**
     * 根据request请求参数的key值得到values值
     *
     * @param paramMap
     * @param key
     * @return
     */
    public static String getValues(Map paramMap, Object key) {
        Object obj2 = paramMap.get(key);
        String[] oo = (String[]) obj2;
        StringBuffer values = new StringBuffer();
        for (String string : oo) {
            values.append(string);
        }
        return values.toString();
    }

    /**
     * 获得服务器端ip地址
     *
     * @return
     */
//    public static String getServerNameIp() {
//        String ip = null;
//        try {
//            ip = getServerIp();
//            if (StringUtils.isEmpty(ip)) {
//                ip = getLocalIp();
//            }
//        } catch (Throwable e) {
//            // 有异常时获得本地ip
//            ip = getLocalIp();
//        }
//        return ip;
//    }

    /**
     * 获得本机ip
     *
     * @return
     */
    public static String getLocalIp() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ipAddrStr = "";
        byte[] ipAddr = null;
        if (addr != null) {
            ipAddr = addr.getAddress();
            for (int i = 0; i < ipAddr.length; i++) {
                if (i > 0) {
                    ipAddrStr += ".";
                }
                ipAddrStr += ipAddr[i] & 0xFF;
            }
            // System.out.println(ipAddrStr);
        }

        return ipAddrStr;
    }

    public static boolean isLog(HttpServletRequest request) {
        Map paramMap = request.getParameterMap();
        String servletPath = request.getServletPath();
        Set keySet = paramMap.keySet();
        boolean flag = true;
        for (Object o : keySet) {
            if (servletPath.contains("query") && "_queryable".equals(o.toString()) && (request.getAttribute("autoSkip") != null)) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 检验登录状态
     *
     * @return
     */
    public static Boolean checkLoginFlag(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute(SessionConstants.OPERATOR_DTO) == null || session.getAttribute(SessionConstants.CUSTOMER_DTO) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static void main(String[] args) throws SocketException {
        System.out.println(getServerIp());
    }

    /**
     * 得到服务器ip
     *
     * @return IP Address
     * @throws SocketException
     */
    private static String getServerIp() throws SocketException {
        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        String ip = null;
        while (enumeration.hasMoreElements()) {
            NetworkInterface networkInterface = enumeration.nextElement();
            if (networkInterface.isUp()) {
                Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
                while (addressEnumeration.hasMoreElements()) {
                    InetAddress nextElement = addressEnumeration.nextElement();
                    if (nextElement.toString().contains(":") || nextElement.toString().contains("127.0.0.1")) {
                        continue;
                    }
                    ip = nextElement.toString().startsWith("/") ? nextElement.toString().substring(1, nextElement.toString().length()) : nextElement.toString();
                }
            }
        }

        return ip;
    }

    /**
     *
     * @param response
     * @param success
     * @param message
     */
    public static void writeG3AjaxResponse(HttpServletResponse response, boolean success, String message) {

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("success", success);
        result.put("message", message);
        String json;
        try {
            json = JSON.json(result);
            response.getWriter().print(json);
        } catch (Throwable e) {
            logger.info("输出ajax信息出错!");
        }

    }

//    public static String getGuid() {
//        String threadUID = "";
//        try {
//            ThreadContext context = ThreadContextUtils.getContext();
//            if (context != null) {
//                threadUID = context.getThreadUID();
//            } else {
//                logger.info("this context is null");
//            }
//        } catch (Throwable e) {
//            logger.error("get guid is error", e);
//        }
//        return threadUID;
//    }

    /**
     * 获取本地静态资源
     * 1.用于获得拦截器内css，js，image等文件信息
     *
     * @param response
     * @param request
     * @throws IOException
     */
    public static void getResources(HttpServletResponse response, HttpServletRequest request) throws IOException {
        // 返回读取指定资源的输入流
        InputStream is = UrlUtils.class.getResourceAsStream(request.getServletPath());
        String resource = request.getServletPath();
        String contentType = UrlUtils.formartContentType(resource);
        response.setContentType(contentType);
        ServletOutputStream out = response.getOutputStream();
        byte[] c = new byte[1024];
        while (true) {
            int read = is.read(c, 0, c.length);
            if (read == -1) {
                break;
            }
            out.write(c, 0, read);
        }
    }

    /**
     * 获取自定义应用路径
     *
     * @return
//     */
//    public static String getAppDefineContext(String appUrl, String context) {
//        if (StringUtils.isNotEmpty(appUrl)) {
//            context = appUrl.substring(0, appUrl.lastIndexOf("/")) + "/" + context;
//        }
//        return context;
//    }
}
