# spring
Spring学习



# 1 JavaBean、SpringBean、对象之间的区别

**对象：**Bean肯定是对象。

**JavaBean：**所有的属性都是私有的，并提供get和set方法。

**SpringBean：**由Spring框架生成的对象。



# 2 Bean定义

1. bean标签：<bean/>

   bean标签相关的配置文件的内容如下：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://www.springframework.org/schema/beans
                              http://www.springframework.org/schema/beans/spring-beans.xsd">
       <bean id="user" class="com.luban.User"/>
   </beans
   ```

   获取的代码如下：

   ```java
   ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
   User user1 = applicationContext.getBean("user", User.class);
   System.out.println(user1);
   ```

2. @Component（@Service、@Controller）注解

   @Component注解的代码如下：

   ```java
   @Component
   public class User {
       private String name;
       public String getName() {
           return name;
       }
       public void setName(String name) {
           this.name = name;
       }
   }
   ```

   配置类的代码如下：

   ```java
   @ComponentScan("com.luban")
   public class Config {
       @Bean
       public User user() {
           return new User();
       }
   }
   ```

   获取的代码如下：

   ```java
   AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
   User user2 = context.getBean("user", User.class);
   System.out.println(user2);
   ```

3. @Bean注解

   @Bean注解的代码如下：

   ```java
   public class Config {
       @Bean
       public User user() {
           return new User();
       }
   }
   ```

   获取的代码如下：

   ```java
   AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(Config.class);
   User user2 = context.getBean("user", User.class);
   System.out.println(user2);
   ```





# Bug1：*--2022.2.21*

```
警告: Exception encountered during context initialization - cancelling refresh attempt:org.springframework.beans.factory.UnsatisfiedDependencyException:Error creating bean with name 'userDaoImpl':Unsatisfied dependency expressed through field 'jdbcTemplate';nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:No qualifying bean of type 'org.springframework.jdbc.core.JdbcTemplate' available:expected at least 1 bean which qualifies as autowire candidate.Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
```

解决方法：出现这个异常是有的方法上面没有写上@Bean注解，在相应的方法上面加上@Bean注解。



# Bug2：*--2022.2.22*

```
org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'org.springframework.transaction.TransactionManager' available: expected single matching bean but found 2: transactionManager,getDataSourceTransactionManager
```

解决方法：出现这个异常是对应的类上面写上了@Transactional注解，将该@Transactional注解去掉或者将配置类中的创建事务管理器方删除即可。

