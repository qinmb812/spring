package com.qin.demo1;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class QinBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        if ("userService".equals(beanName)) {
            System.out.println(beanName);
            System.out.println(bean);
        }

        Class<?> clazz = bean.getClass();   // UserService.class
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(QinValue.class)) {
                QinValue fieldAnnotation = field.getAnnotation(QinValue.class);
                String value = fieldAnnotation.value();
                System.out.println(value);
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
