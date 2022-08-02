package com.qin.demo1;

import org.springframework.beans.factory.FactoryBean;

public class QinFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        Person person = new Person();
        System.out.println(person);
        return person;
    }

    @Override
    public Class<?> getObjectType() {
        return Person.class;
    }
}
