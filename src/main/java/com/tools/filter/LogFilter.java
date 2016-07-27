package com.tools.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class LogFilter implements Filter{
    
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);
    
    private String flag;
    
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        logger.info("*******   Log Filter Start!");
        // 将请求转换成HttpServletRequest 请求  
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 记录日志
        if("true".equals(flag)) {
            logger.info("*******   Log Filter已经截获到用户的请求的地址:" + httpServletRequest.getServletPath() );
        }
        try {
            // Filter 只是链式处理，请求依然转发到目的地址。  
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("*******   Log Filter End!");
    }
   
    public void init(FilterConfig config) throws ServletException {
        logger.info("*******   Init the log filter!");
        flag = config.getInitParameter("flag");
        logger.info("*******   flag : " + flag);
    }

    public void destroy() {
        logger.info("*******   Destroy the log filter!");
        this.flag = null;
    }
}
