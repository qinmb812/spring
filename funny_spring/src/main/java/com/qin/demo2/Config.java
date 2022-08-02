package com.qin.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.qin.demo2")
public class Config {
    @Bean
    public User user() {
        return new User();
    }

    @Bean
    public User user2() {
        return new User();
    }

    @Bean
    public Person person() {
        return new Person();
    }
}
