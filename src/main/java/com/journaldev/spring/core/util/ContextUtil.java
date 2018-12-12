package com.journaldev.spring.core.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ConfigurableWebApplicationContext;

/**
 * 
 *讀取Bean用 e.g. User user = ((UserDao) ContextUtil.getBean(UserDao.class)).findOneByAccount(account);
 */
@Component
public class ContextUtil {
    private static ContextUtil instance;
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void registerInstance() {
        instance = this;
        if (instance.applicationContext instanceof ConfigurableWebApplicationContext) {
            ((ConfigurableWebApplicationContext) instance.applicationContext).registerShutdownHook();
        }

    }

    public static Object getBean(String name) {
        return instance.applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return instance.applicationContext.getBean(clazz);
    }
}