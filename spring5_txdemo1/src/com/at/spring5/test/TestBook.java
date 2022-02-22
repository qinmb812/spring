package com.at.spring5.test;

import com.at.spring5.config.TxConfig;
import com.at.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
     * 出现这个问题将对应的@Transactional注解去掉
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
}
