package com.at.spring5.service;

import com.at.spring5.dao.UserDao;
import com.at.spring5.dao.UserDaoImpl;

public class UserService {
    // 创建UserDao对象属性，生成set方法
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("Service add ............");
        // 原始方法：创建UserDao对象
        //        UserDao userDao = new UserDaoImpl();
        userDao.update();
    }
}
