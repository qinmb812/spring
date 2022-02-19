package com.at.spring5;

public class User {
    private String userName;

    //基于xml创建对象的时候，默认调用的是无参构造方法完成对象创建
//    public User(String userName) {
//        this.userName = userName;
//    }

    public void add() {
        System.out.println("add......");
    }
}
