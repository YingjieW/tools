package com.tools.interceptor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RequestInterceptorTest extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestInterceptorTest.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String className = "";
        Object target = null;
        try {
            if (handler instanceof HandlerMethod) {
                target = ((HandlerMethod) handler).getBean();
                className = target.getClass().getSimpleName();
                logger.info("###   className: " + className);
                logger.info("###   handler: " + JSON.toJSONString(handler));
            }
        } catch (Throwable t) {
            logger.error("###   RequestInterceptorTest.preHandle unknown exception.", t);
        }
        return true;  
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("###   postHandle....");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("###   afterCompletion....");
    }
}
