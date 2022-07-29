package com.luban;

import org.springframework.context.annotation.Bean;

public class Config {
    @Bean
    public User user() {
        return new User();
    }
}
