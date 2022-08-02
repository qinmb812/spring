package com.qin.demo2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private User user;

    public UserService() {
        System.out.println("无参构造方法");
    }

    @Autowired(required = false)
    public UserService(User user) {
        this.user = user;
        System.out.println("一个参数的构造方法");
    }

    /**
     * 使用了@Autowired(required = false)注解来推断构造方法：
     *      1、首先是选择参数个数多的，然后再判断这些参数多个可不可用
     *          （先根据类型去找Bean。如果只有一个Bean，就可用；如果有多个Bean，就根据参数名去找Bean【使用的是@Bean注解的话，默认是方法名是Bean的名字】）。
     *              如果可用，就选择该构造方法；
     *              如果不可用，就重复当前这一步。
     *      2、如果参数个数相同，推断的构造方法就与相关方法的顺序有关
     * @param user
     * @param user2
     */
    @Autowired(required = false)
    public UserService(User user, User user2) {
        this.user = user;
        System.out.println("两个参数的构造方法");
    }

    @Autowired(required = false)
    public UserService(User user, Person person) {
        this.user = user;
        System.out.println("person两个参数的构造方法");
    }
}
