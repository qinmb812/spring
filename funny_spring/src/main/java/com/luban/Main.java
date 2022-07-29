package com.luban;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        User user = new User(); // 对象   Bean肯定是对象

        // JavaBean 所有属性都是私有的，提供get和set方法
        String name = user.getName();

        // Spring框架生成的对象 --> SpringBean
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user1 = applicationContext.getBean("user", User.class);
    }
}
