package com.qin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.qin")
public class Config {
    @Bean
    public User user() {
        return new User();
    }
}
