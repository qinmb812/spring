package com.luban;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        User user = new User(); // 对象   Bean肯定是对象

        // JavaBean 所有属性都是私有的，提供get和set方法
        String name = user.getName();

        // Spring框架生成的对象 --> SpringBean
        // 通过bean标签定义Bean
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user1 = applicationContext.getBean("user", User.class);    // 根据定义的Bean去生成Bean对象
        System.out.println(user1);

        // 通过@Bean注解定义Bean
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        User user2 = context.getBean("user", User.class);
        System.out.println(user2);

        // 编程式  TransactionManager
        // 声明式  @Transactional
        // BeanDefinition   编程式
        AnnotationConfigApplicationContext applicationContext1 = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        beanDefinition.setBeanClass(User.class);
        applicationContext1.registerBeanDefinition("user", beanDefinition);
        applicationContext1.refresh();
        User user3 = applicationContext1.getBean("user", User.class);
        System.out.println(user3);

        AnnotationConfigApplicationContext applicationContext2 = new AnnotationConfigApplicationContext();
        AbstractBeanDefinition beanDefinition2 = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
        // 2个Bean对象     &user:QinFactoryBean类型的对象，    user:person
        beanDefinition2.setBeanClass(QinFactoryBean.class);
        applicationContext2.registerBeanDefinition("user", beanDefinition2);
        applicationContext2.refresh();
        QinFactoryBean user4 = applicationContext2.getBean("&user", QinFactoryBean.class);
        System.out.println(user4);
        Person person = applicationContext2.getBean("user", Person.class);
        System.out.println(person);
    }
}
