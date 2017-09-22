package com.tools.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/8/22 下午5:02
 */
@Component
public class BeanFactoryUtil implements ApplicationContextAware {

    private static final Logger logger = LoggerFactory.getLogger(BeanFactoryUtil.class);

    // spring 上下文对象
    private static ApplicationContext applicationContext;
    // 本地缓存
    private static final Map<String, Object> INSTANCE_MAP = new ConcurrentHashMap<String, Object>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    /**
     * 根据类唯一标识名获取Bean
     * @param beanName 唯一标识名称（ID）
     * @return Object
     * @throws BeansException
     */
    public static Object getBeanByName(String beanName) throws BeansException{
        return applicationContext.getBean(beanName);
    }

    /**
     * 根据类Class获取Bean
     * @param requiredType 类Class
     * @param <T> 类型
     * @return
     * @throws BeansException
     */
    public static <T> T getBeanByClass(Class<T> requiredType) throws BeansException{
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据类唯一标识名及类Class获取Bean
     * @param beanName 唯一标识名
     * @param requiredType 类Class
     * @param <T> 类型
     * @return
     * @throws BeansException
     */
    public static <T> T getBeanByNameAndClass(String beanName, Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(beanName, requiredType);
    }

    /**
     * 根据className,获取其实体
     * @param className
     * @return
     * @throws ClassNotFoundException
     */
    public static Object getInstanceByClassName(String className) throws ClassNotFoundException {
        Object obj = INSTANCE_MAP.get(className);
        if (obj != null) {
            return obj;
        }
        Class clazz = null;
        try {
            clazz = Class.forName(className);
            obj = applicationContext.getBean(clazz);
            if (obj == null) {
                throw new NoSuchBeanDefinitionException(className);
            }
            INSTANCE_MAP.put(className, obj);
            return obj;
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (BeansException e) {
            logger.info("no bean[" + className + "] found in spring, create one.");
            try {
                obj = clazz.newInstance();
                INSTANCE_MAP.put(className, obj);
                return obj;
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
