package com.tools.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterTest implements Filter{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FilterTest.class);
    
    public void destroy() {
        LOGGER.info("*******   Destroy FilterTest!");
    }
   
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        LOGGER.info("*******   FilterTest Start!");
        // 将请求转换成HttpServletRequest 请求  
        try {
            // Filter 只是链式处理，请求依然转发到目的地址。  
            chain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LOGGER.info("*******   FilterTest End!");
    }
   
    public void init(FilterConfig config) throws ServletException {
        LOGGER.info("*******   Init FilterTest!");
    }
}
