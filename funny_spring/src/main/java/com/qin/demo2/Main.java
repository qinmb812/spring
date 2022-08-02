package com.qin.demo2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext8 = new AnnotationConfigApplicationContext(Config.class);
        UserService userService = applicationContext8.getBean("userService", UserService.class);
        System.out.println(userService);
    }
}
