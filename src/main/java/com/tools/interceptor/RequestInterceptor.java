package com.tools.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器
 * @author：YJ    
 * @since：2016年2月24日 下午11:26:23 
 * @version:
 */
public class RequestInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
        String className = "";
        Object target = null;
        if (handler instanceof HandlerMethod) {
            target = ((HandlerMethod) handler).getBean();
            className = target.getClass().getSimpleName();
            // 防止代理类名异常
            //className = className.substring(0, className.indexOf("$") == -1 ? className.length() : className.indexOf("$"));
            LOGGER.info("==============   className: " + className);
        }
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {  
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {  
    }  
}
