package com.oa.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@Component
public class SpringContextBean implements ApplicationContextAware {
    private static ApplicationContext context = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        context = applicationContext;
    }

    public static <T> T getBean(String name)
    {
        return (T)context.getBean(name);
    }

    public static <T> T getBean(Class<T> beanClass){
        return context.getBean(beanClass);
    }
}
