package com.at.spring5;

/**
 * 使用set进行属性注入
 */
public class Book {
    //创建属性
    private String bname;
    private String bauthor;

    //创建属性对应的set方法
    public void setBname(String bname) {
        this.bname = bname;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    public void testDemo() {
        System.out.println(bname + "::" + bauthor);
    }
}
