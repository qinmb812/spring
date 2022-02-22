package com.at.spring5.test;

import com.at.spring5.config.TxConfig;
import com.at.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class TestBook {
    @Test
    public void testAccount() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }
    /*
     * org.springframework.beans.factory.NoUniqueBeanDefinitionException:
     *      No qualifying bean of type 'org.springframework.transaction.TransactionManager' available:
     *          expected single matching bean but found 2:
     *              transactionManager,getDataSourceTransactionManager
     *
     * 出现这个问题将对应的@Transactional注解去掉或者将配置类中的创建事务管理器方法删除
     */

    @Test
    public void testAccount2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }

    @Test
    public void testAccount3() {
        ApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = context.getBean("userService", UserService.class);
        userService.accountMoney();
    }

    // 函数式风格创建对象，交给Spring来管理
    @Test
    public void testGenericApplicationContext() {
        // 1 创建GenericApplicationContext对象
        GenericApplicationContext context = new GenericApplicationContext();
        // 2 调用context的方法注册对象
        context.refresh();
//        context.registerBean(User.class, () -> new User());
        context.registerBean("user1", User.class, () -> new User());
        // 3 获取在Spring注册的对象
//        User user = (User) context.getBean("com.at.spring5.test.User");
        User user = (User) context.getBean("user1");
        System.out.println(user);
    }
}
