package com.tools.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogFilter implements Filter{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);
    
    private String flag;
    
    public void destroy() {
        LOGGER.info("*******   Destroy the log filter!");
        this.flag = null;
    }
   
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("*******   Log Filter Start!");
        // 将请求转换成HttpServletRequest 请求  
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 记录日志
        if("true".equals(flag)) {
            LOGGER.info("*******   Log Filter已经截获到用户的请求的地址:" + httpServletRequest.getServletPath() );
        }
        try {
            // Filter 只是链式处理，请求依然转发到目的地址。  
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("*******   Log Filter End!");
    }
   
    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("*******   Init the log filter!");
        flag = config.getInitParameter("flag");
        LOGGER.info("*******   flag : " + flag);
    }
}
