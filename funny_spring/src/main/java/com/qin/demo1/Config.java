package com.qin.demo1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.qin.demo1")
public class Config {
    @Bean
    public User user() {
        return new User();
    }
}
