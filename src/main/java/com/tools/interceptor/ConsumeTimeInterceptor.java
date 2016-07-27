package com.tools.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ConsumeTimeInterceptor extends HandlerInterceptorAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsumeTimeInterceptor.class);
    
    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("StartTime_Watcher");
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 开始时间
        long beginTime = System.currentTimeMillis();
        //线程绑定变量（该数据只有当前请求的线程可见）
        startTimeThreadLocal.set(beginTime);
        //继续流程
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("============postHandle.....");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 结束时间
        long endTime = System.currentTimeMillis();
        //得到线程绑定的局部变量（开始时间）
        long beginTime = startTimeThreadLocal.get();
        long consumeTime = endTime - beginTime;
        logger.info("==============   Consume Time : " + consumeTime + " mills.");
    }  
}
