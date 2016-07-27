package com.tools.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/27 下午5:37
 */
public class ServletRequestListenerImpl implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(ServletRequestListenerImpl.class);

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {
        logger.info("======== Request Destroyed Time: " + System.currentTimeMillis());
    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        logger.info("======== Request Initialized Time: " + System.currentTimeMillis());
    }
}
