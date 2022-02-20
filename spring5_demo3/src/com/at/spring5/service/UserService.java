package com.at.spring5.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// 在注解里面value属性值可以不写
// 默认值是类名称，首字母小写
// UserService -- userService
// @Component(value = "userService")   // <bean id="userService" class=".."/>
@Service
public class UserService {
    public void add() {
        System.out.println("service add .........");
    }
}
