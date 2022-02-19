package com.at.spring5;

/**
 * 使用有参数的构造注入
 */
public class Order {
    private String oname;
    private String address;

    public Order(String oname, String address) {
        this.oname = oname;
        this.address = address;
    }

    public void orderTest() {
        System.out.println(oname + "::" + address);
    }
}
