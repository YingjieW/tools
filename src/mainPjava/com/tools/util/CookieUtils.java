package com.tools.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/1/19 下午7:12
 */
public class CookieUtils {


    private static final int DEFAULT_MAX_AGE = 30 * 60;

    public static final String DEFAULT_COOKIE_PATH = "/";

    /**
     * 如果您在cookie中设置了HttpOnly属性，那么通过js脚本将无法读取到cookie信息，这样能有效的防止XSS攻击
     */
    public static void put(HttpServletResponse response, String key, String value, Integer maxAge) {
        // 所有应用通用cookie路径
        response.addHeader("Set-Cookie", key + "=" + value + ";Path=" + DEFAULT_COOKIE_PATH + "; Max-Age=" + maxAge + "; HttpOnly");
    }

    /**
     * 从cookie中取值
     *
     * @param key
     * @return
     */
    public static Object get(HttpServletRequest request, String key) {
        // 获取request里面的cookie
        Cookie[] cookie = request.getCookies();
        if (cookie != null) {
            for (int i = 0; i < cookie.length; i++) {
                Cookie cook = cookie[i];
                if (cook.getName().equalsIgnoreCase(key)) { // 获取键
                    return cook.getValue();
                }
            }
        }
        return "";
    }

    public static void delete(HttpServletRequest request, HttpServletResponse response, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(key)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static void add(HttpServletResponse response, String key, String value, String path){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(DEFAULT_MAX_AGE);
        if (StringUtils.isNotBlank(path)) {
            cookie.setPath(path);
        }
        response.addCookie(cookie);
    }
}
