# spring
Spring学习



# 1 定义Bean

## 1.1 JavaBean、SpringBean、对象之间的区别

**对象：**Bean肯定是对象。

**JavaBean：**所有的属性都是私有的，并提供get和set方法。

**SpringBean：**由Spring框架生成的对象。

## 1.2 Bean定义

1. bean标签（<bean/>）：声明式方式定义Bean。在Spring的xml中定义Bean，Spring读取类中的构造方法建造的对象。

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

2. @Component（@Service、@Controller）注解：声明式方式定义Bean。在一个方法上，方法中new出来的一个对象。

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

3. @Bean注解：声明式方式定义Bean。放在类上，注入一个Bean。

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

4. BeanDefinition：编程式方式定义Bean

   代码如下：

   ```java
   AnnotationConfigApplicationContext applicationContext1=new AnnotationConfigApplicationContext();
   AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
   beanDefinition.setBeanClass(User.class);
   applicationContext1.registerBeanDefinition("user",beanDefinition);
   applicationContext1.refresh();
   User user3 = context.getBean("user", User.class);
   System.out.println(user3);
   ```

5. FactoryBean：通过实现FactoryBean接口。首先它是一个Bean，但又不仅仅是一个Bean。它是一个能生产或修饰对象生成的工厂Bean，类似于设计模式中的工厂模式和装饰器模式。它能在需要的时候生产一个对象，且不仅仅限于它自身，它能返回任何Bean的实例。

   实现FactoryBean接口的类如下：

   ```java
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
   ```

   获取的代码如下：

   ```java
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
   ```

6. Supplier：Spring提供registerBean方法，将FactoryBean中的方式进行封装，以一种简单的方式注册Bean。获取Bean的时候用user是因为Spring默认名字是根据传入的类名(User.class)决定的。

   实现的代码如下：

   ```java
   AnnotationConfigApplicationContext applicationContext3 = new AnnotationConfigApplicationContext();
   applicationContext3.registerBean(User.class, new Supplier<User>() {
       @Override
       public User get() {
           User u = new User();
           u.setName("xxxxxxx");
           return u;
       }
   });
   applicationContext3.refresh();
   User user5 = applicationContext3.getBean("user", User.class);
   System.out.println(user5);
   System.out.println(user5.getName());
   ```



# 2 Spring容器

## 2.1 单例池、单例Bean、单例模式的区别

单例Bean等同于Spring容器中只有一个某个类的对应的Bean么？ 并不等同。

例如：xml的标签如下：

```xml
<bean id="user" class="com.luban.User"/>
<bean id="user2" class="com.luban.User"/>
```

测试代码如下：

```java
ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
// User     单例Bean == Spring容器里只能有一个User类型的Bean ? 0
System.out.println(applicationContext.getBean("user", User.class));
System.out.println(applicationContext.getBean("user2", User.class));
```

容器中的单例概念是作用在注册的Bean上，而不是类上。





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

