package com.qin.demo2;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class QinBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            System.out.println("实例化前");
//            return new Object();    // 这里执行了return，那么实例化、实例化后、初始化前、初始化这些步骤都不会执行。而初始化后还会执行。
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            System.out.println("实例化后");
        }
        return true;
    }
}
