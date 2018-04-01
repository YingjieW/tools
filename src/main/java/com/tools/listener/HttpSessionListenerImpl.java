package com.tools.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/27 下午6:20
 */
public class HttpSessionListenerImpl implements HttpSessionListener{
    /**
     * 存在并发问题，需要加锁。
     */
    // 利用HttpSessionListener统计最多在线用户人数
    // from: http://tianweili.github.io/blog/2015/01/27/java-listener/
    public void sessionCreated(HttpSessionEvent event) {
        ServletContext app = event.getSession().getServletContext();
        int count = Integer.parseInt(app.getAttribute("onLineCount").toString());
        count++;
        app.setAttribute("onLineCount", count);
        int maxOnLineCount = Integer.parseInt(app.getAttribute("maxOnLineCount").toString());
        if (count > maxOnLineCount) {
            //记录最多人数是多少
            app.setAttribute("maxOnLineCount", count);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //记录在那个时刻达到上限
            app.setAttribute("date", df.format(new Date()));
        }
    }
    //session注销、超时时候调用，停止tomcat不会调用
    public void sessionDestroyed(HttpSessionEvent event) {
        ServletContext app = event.getSession().getServletContext();
        int count = Integer.parseInt(app.getAttribute("onLineCount").toString());
        count--;
        app.setAttribute("onLineCount", count);

    }
}
