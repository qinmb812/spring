package com.luban;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.luban")
public class Config {
    @Bean
    public User user() {
        return new User();
    }
}
