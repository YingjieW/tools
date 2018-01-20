package com.tools.util;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/1/19 下午7:12
 */
public class SessionUtils {

    public static void clear(HttpSession session) {
        if (session != null) {
            Enumeration em = session.getAttributeNames();
            if (em != null) {
                Object element = null;
                while (em.hasMoreElements()) {
                    element = em.nextElement();
                    if (element != null) {
                        session.removeAttribute(element.toString());
                    }
                }
            }
        }
    }

    public static Map<String, Object> toMap(HttpSession session) {
        if (session == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<String, Object>();
        Enumeration em = session.getAttributeNames();
        if (em != null) {
            Object element = null;
            while (em.hasMoreElements()) {
                element = em.nextElement();
                if (element != null) {
                    result.put(element.toString(), session.getAttribute(element.toString()));
                }
            }
        }
        return result;
    }
}
