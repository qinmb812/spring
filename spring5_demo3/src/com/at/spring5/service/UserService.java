package com.at.spring5.service;

import com.at.spring5.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

// 在注解里面value属性值可以不写
// 默认值是类名称，首字母小写
// UserService -- userService
// @Component(value = "userService")   // <bean id="userService" class=".."/>
@Service
public class UserService {
//    @Autowired    // 根据类型进行注入
//    @Qualifier("userDaoImpl")   // 根据名称进行注入
//    private UserDao userDao;

    //    @Resource   // 根据类型进行注入
    @Resource(name = "userDaoImpl")
    private UserDao userDao;

    public void add() {
        System.out.println("service add .........");
        userDao.add();
    }
}
