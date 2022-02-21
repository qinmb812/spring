package com.at.spring5.aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 增强的类
 */
@Component
@Aspect // 生成代理对象
@Order(3)
public class UserProxy {
    // 相同切入点抽取
    @Pointcut("execution(* com.at.spring5.aopanno.User.add(..))")
    public void pointdemo() {
    }

    // 前置通知
    // @Before注解表示作为前置通知
//    @Before("execution(* com.at.spring5.aopanno.User.add(..))")
    @Before("pointdemo()")
    public void before() {
        System.out.println("before ..........");
    }

    // 最终通知     有异常照样执行
    @After("execution(* com.at.spring5.aopanno.User.add(..))")
    public void after() {
        System.out.println("after ..........");
    }

    // 后置通知（返回通知）   有异常不执行
    @AfterReturning("execution(* com.at.spring5.aopanno.User.add(..))")
    public void afterReturning() {
        System.out.println("afterReturning ..........");
    }

    // 异常通知
    @AfterThrowing("execution(* com.at.spring5.aopanno.User.add(..))")
    public void afterThrowing() {
        System.out.println("afterThrowing ..........");
    }

    // 环绕通知
    @Around("execution(* com.at.spring5.aopanno.User.add(..))")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前 ..........");
        // 被增强的方法执行
        proceedingJoinPoint.proceed();
        System.out.println("环绕之后 ..........");
    }
}
