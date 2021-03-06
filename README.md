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

3. @Bean注解：声明式方式定义Bean。放在类上，注入一个Bean。默认情况下bean的名称和方法名称相同，添加的bean的id为方法名。

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

1. 单例Bean等同于Spring容器中只有一个某个类的对应的Bean么？ 并不等同。

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

2. 与单例概念不同的是原型（多例）的概念。Spring容器默认是单例模式来实现Bean的。

   例如：定义的配置文件如下：

   ```xml
   <bean id="user" class="com.luban.User" scope="prototype"/>
   <bean id="user2" class="com.luban.User"/>
   ```

   测试代码如下：

   ```java
   ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
   // 单例   原型（多例）
   // 单例池  ConcurrentHashMap   singletonObject beanName Object     非懒加载的单例Bean
   System.out.println(applicationContext.getBean("user", User.class));
   System.out.println(applicationContext.getBean("user", User.class));
   System.out.println(applicationContext.getBean("user2", User.class));
   System.out.println(applicationContext.getBean("user2", User.class));
   ```

   如果对bean注册时，scope(作用域)的值为prototype时，则是代表注册的是原型（多例）模式的Bean。

   有些时候，可以把原型模式当作Java的new操作。即每次getBean的时候都相当于new一个Java对象——一个不同的Bean。

   单例池其实就是一个保存单例Bean的池子，在Spring程序运行时就加载了，并将非懒加载的单例Bean放到容器中。它的底层是ConcurrentHashMap。singletonObjects : key : beanName value : Object 这种Map的保存方式就是保持单例的原因。对于一些懒加载的单例Bean则是啥时候用了就啥时候放到容器中。

3. 例如，有一个Bean，类型为User，则：

   单例Bean：Spring容器中可以有多个beanName不同，但是类型相同的bean。例如，可以有beanName分别为user1和user2，对应的类型都是User。

   单例模式：是指每次创建的对象都是同一个。

   单例模式是指在一个JVM进程中仅有一个实例，而单例bean是指在一个Spring Bean容器(ApplicationContext)中仅有一个实例。

   单例池：spring源码中的定义为Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256)。其意义即为存储Spring生成的单例Bean。

## 2.2 BeanFactory

Bean工厂-->生产Bean的。

> BeanFacotry是spring中比较原始的Factory。如XMLBeanFactory就是一种典型的BeanFactory。原始的BeanFactory无法支持spring的许多插件，如AOP功能、Web应用等。 ApplicationContext接口（应用程序上下文-IOC容器），它由BeanFactory接口派生而来，ApplicationContext包含BeanFactory的所有功能，通常建议比BeanFactory优先。

例如：

```java
DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
// beanFactory.registerSingleton("user", new User());
AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.genericBeanDefinition().getBeanDefinition();
beanDefinition1.setBeanClass(User.class);
beanFactory.registerBeanDefinition("user", beanDefinition1);
User user6 = beanFactory.getBean("user", User.class);
System.out.println(user6);
```

BeanFactory就是一个容器，它可以通过注册BeanDefinition的方式注册Bean，或者直接注册Bean对象，而后者只是前者的封装。

## 2.3 BeanFactory与FactoryBean之间的区别

- BeanFactory

  > BeanFactory是IOC最基本的容器，负责**生产和管理bean**，它为其他具体的IOC容器实现提供了最基本的规范，例如DefaultListableBeanFactory,XmlBeanFactory, ApplicationContext 等具体的容器都是实现了BeanFactory，再在其基础之上附加了其他的功能。

  BeanFactory源码：

  ```java
  public interface BeanFactory {
      String FACTORY_BEAN_PREFIX = "&";
      Object getBean(String var1) throws BeansException;
      <T> T getBean(String var1, Class<T> var2) throws BeansException;
      Object getBean(String var1, Object... var2) throws BeansException;
      <T> T getBean(Class<T> var1) throws BeansException;
      <T> T getBean(Class<T> var1, Object... var2) throws BeansException;
      <T> ObjectProvider<T> getBeanProvider(Class<T> var1);
      <T> ObjectProvider<T> getBeanProvider(ResolvableType var1);
      boolean containsBean(String var1);
      boolean isSingleton(String var1) throws NoSuchBeanDefinitionException;
      boolean isPrototype(String var1) throws NoSuchBeanDefinitionException;
      boolean isTypeMatch(String var1, ResolvableType var2) throws NoSuchBeanDefinitionException;
      boolean isTypeMatch(String var1, Class<?> var2) throws NoSuchBeanDefinitionException;
      @Nullable
      Class<?> getType(String var1) throws NoSuchBeanDefinitionException;
      @Nullable
      Class<?> getType(String var1, boolean var2) throws NoSuchBeanDefinitionException;
      String[] getAliases(String var1);
  }
  ```

- FactoryBean

  > FactoryBean是一个接口，当在IOC容器中的Bean实现了FactoryBean接口后，通过getBean(String BeanName)获取到的Bean对象并不是FactoryBean的实现类对象，而是这个实现类中的getObject()方法返回的对象。要想获取FactoryBean的实现类，就要getBean(&BeanName)，在BeanName之前加上&。
  
  FactoryBean源码：
  
  ```java
  public interface FactoryBean<T> {
      String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";
      @Nullable
      T getObject() throws Exception;
      @Nullable
      Class<?> getObjectType();
      default boolean isSingleton() {
          return true;
      }
  }
  ```

## 2.4 ApplicationContext与BeanFactory的联系和区别

```java
public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory, MessageSource, ApplicationEventPublisher, ResourcePatternResolver {}
public interface ListableBeanFactory extends BeanFactory {}
public interface HierarchicalBeanFactory extends BeanFactory {}
```

ApplicationContext继承了ListableBeanFactory、HierarchicalBeanFactory，而ListableBeanFactory、HierarchicalBeanFactory又继承了BeanFactory。

Application 继承了很多接口，集成了很多功能，例如：

- MessageSource：国际化。
- ApplicationEventPublisher：事件发布。

## 2.5 ApplicationContext

Application与BeanFactory非常的像，比如可以注册Bean，获得Bean等操作（包括BeanDefinition）。

其他功能：获取系统的环境变量、JVM变量信息、发布事件、获取资源、国际化。

```java
AnnotationConfigApplicationContext applicationContext4 = new AnnotationConfigApplicationContext();
applicationContext4.refresh();
// 获取系统的环境变量
System.out.println(applicationContext4.getEnvironment().getSystemEnvironment());
// 获取JVM的环境变量
System.out.println(applicationContext4.getEnvironment().getSystemProperties());
// 获取资源
Resource resource = applicationContext4.getResource("D:\\workspace\\learning\\spring_learning\\funny_spring\\src\\main\\resources\\spring.xml");
System.out.println(resource);
// 国际化
// test==test
// test==测试
applicationContext4.getMessage("test", null, Locale.CHINESE);
```

**第一种分类方式：**可不可以刷新

refresh的作用？

相当于重启了容器，先将容器中的内容（BeanDefinition和Bean）清空掉，然后根据target下的已经的xml文件或者编译好的class文件来进行Bean的注入等操作，就相当于电脑系统重启是一样的，把内存清空，根据磁盘内容重新载入。所以视频中的两次getBean获得的bean是不一样的。

- GenericApplicationContext：AnnotationConfigApplicationContext-->GenericApplicationContext。

  调用refresh方法会报错，错误信息如下：

  ```java
  Exception in thread "main" java.lang.IllegalStateException: GenericApplicationContext does not support multiple refresh attempts: just call 'refresh' once
  ```

- AbstractRefreshableApplicationContext：ClassPathXmlApplicationContext-->AbstractXmlApplicationContext-->AbstractRefreshableConfigApplicationContext-->AbstractRefreshableApplicationContext。

  测试代码如下：

  ```java
  ClassPathXmlApplicationContext applicationContext7 = new ClassPathXmlApplicationContext("spring.xml");
  System.out.println(applicationContext7.getBean("user"));
  System.out.println(applicationContext7.getBean("user"));
  applicationContext7.refresh();
  System.out.println(applicationContext7.getBean("user"));
  ```

**第二种分类方式：**Spring配置的展现形式：XML、注解

- AnnotationConfigApplicationContext：通过注解（即Java的方式来装配Bean）的方式。

  注解的代码如下：

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

- ClassPathXmlApplicationContext：通过XML文件的方式。使用的是类路径，是类的相对路径。

  ```java
  ClassPathXmlApplicationContext applicationContext5 = new ClassPathXmlApplicationContext("spring.xml");
  System.out.println(applicationContext5.getBean("user"));
  ```

- FileSystemXmlApplicationContext：通过XML文件的方式。使用的是文件的路径，支持绝对路径和相对路径。

  ```java
  // FileSystemXmlApplicationContext applicationContext6 = new FileSystemXmlApplicationContext("D:\\workspace\\learning\\spring_learning\\funny_spring\\src\\main\\resources\\spring.xml");
  FileSystemXmlApplicationContext applicationContext6 = new FileSystemXmlApplicationContext("src/main/resources/spring.xml");
  System.out.println(applicationContext6.getBean("user"));
  ```



# 3 Bean的生命周期

## 3.1 Bean的生命周期概述

生成Bean实际上就是new一个对象、填充属性。

## 3.2 Bean创建的生命周期

1. 生成BeanDefinition

2. 合并BeanDefinition

3. 加载类

4. 实例化前

   实例化前就是指已经获取到了BeanDefinition信息，还没有创建出对象的时候。

   通过实现BeanPostProcessor的子接口InstantiationAwareBeanPostProcessor，来实现实例化前的一些操作。

   测试代码如下：

   ```java
   @Component
   public class QinBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
       @Override
       public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
           if ("userService".equals(beanName)) {
               System.out.println("实例化前");
               // return new Object();    // 这里执行了return，那么实例化、实例化后、初始化前、初始化这些步骤都不会执行。而初始化后还会执行。
           }
           return null;
       }
   }
   ```

5. 推断构造方法

   如果该类没有显示的构造方法，实例化时会调用无参的构造方法；如果有一个显式的构造方法，则会调用该构造方法来进行实例化。当有一个以上的构造方法且均为有参的构造方法时，就会报错啦。只要有显式无参的构造方法就一定会调用无参构造方法来实例化。

   将@Autowired注解加到构造方法上，此时，Spring实现Bean的实例化的时候调用的构造器就是Autowired标注的构造方法了——同理不可以给两个构造方法上都加一个@Autowired注解。该注解通过参数required的值来控制@Autowired是否必须生效。默认required为true，当为false时表示为可选的，当没有为true的@Autowired时，选择权可以交给Spring来选择。

   使用了@Autowired(required = false)注解来推断构造方法：

   - 首先是选择参数个数多的，然后再判断这些参数多个可不可用（先byType，再byName。先根据类型去找Bean。如果只有一个Bean，就可用；如果有多个Bean，就根据参数名去找Bean【使用的是@Bean注解的话，默认是方法名是Bean的名字】）。
     - 如果可用，就选择该构造方法；
     - 如果不可用，就重复当前这一步。

   - 如果参数个数相同，推断的构造方法就与相关方法的顺序有关。

   ```java
   @Component
   public class UserService {
       private User user;
       @Autowired(required = false)
       public UserService(User user) {
           this.user = user;
           System.out.println("一个参数的构造方法");
       }
       /**
        * 使用了@Autowired(required = false)注解来推断构造方法：
        *      1、首先是选择参数个数多的，然后再判断这些参数多个可不可用
        *          （先根据类型去找Bean。如果只有一个Bean，就可用；如果有多个Bean，就根据参数名去找Bean【使用的是@Bean注解的话，默认是方法名是Bean的名字】）。
        *              如果可用，就选择该构造方法；
        *              如果不可用，就重复当前这一步。
        *      2、如果参数个数相同，推断的构造方法就与相关方法的顺序有关
        * @param user
        * @param user2
        */
       @Autowired(required = false)
       public UserService(User user, User user2) {
           this.user = user;
           System.out.println("两个参数的构造方法");
       }
       @Autowired(required = false)
       public UserService(User user, Person person) {
           this.user = user;
           System.out.println("person两个参数的构造方法");
       }
   }
   ```

6. 实例化

7. BeanDefinition的后置处理

   实例化后：只能通过postProcessAfterInstantiation方法对该对象的属性值进行操作，权限范围只局限于传来的对象。通过返回一个布尔型的值，来作为一个开关，返回false时是告诉Spring不需要它再为该属性注入值了，反之Spring可以调用一些方法比如Set方法继续进行属性赋值。

   测试代码如下：

   ```java
   @Component
   public class QinBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
       @Override
       public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
           if ("userService".equals(beanName)) {
               System.out.println("实例化后");
           }
           return true;
       }
   }
   ```

8. 填充属性

9. 执行Aware：
   - BeanNameAware
   - BeanClassLoaderAware
   - BeanFactoryAware

10. 初始化前
    - ApplicationContextAwareProcessor主要也是执行一些其他的Aware
      - EmbeddedValueResolverAware
      - ResourceLoaderAware
      - ApplicationEventPublisherAware
      - MessageSourceAware
      - ApplicationContextAware
    - InitDestroyAnnotationBeanPostProcessor——执行@PostConstruct定义的方法

11. 初始化：
    - 执行InitializingBean接口中的方法
    - 执行BeanDefinition中所定义的初始化方法

12. 初始化后：AbstractAutoProxyCreator——AOP功能实现者

## 3.3 Bean销毁的生命周期

1. 容器关闭
2. 发布ContextClosedEvent事件
3. 调用LifecycleProcessor的onClose方法
4. 销毁单例Bean
   - 找出所有的DisposableBean（实现了DisposableBean接口的Bean）
   - 遍历每个DisposableBean
   - 找出依赖了当前DisposableBean的其他Bean，将这些Bean从单例池中移除掉
   - 调用DisposableBean的destroy()方法
   - 找到当前DisposableBean所包含的inner beans，将这些Bean从单例池中移除掉

## 3.4 Bean的后置处理器

对象实例化之后与该对象变成Bean之前会经过Bean的后置处理器并执行其中的两个方法：

- postProcessBeforeInitialization
- postProcessAfterInitialization

并且是先执行Before再执行After。当重新这俩个方法使得return的对象不为空（默认返回空）时，则Bean就会return的对象，反之根据Bean注册时的配置该怎样就怎样。

当执行SpringBoot的启动方法时，就会将项目中所有的Bean加载，而每一个Bean的加载都会调用上述的两个方法。

看代码中两个方法的参数，一个是对象，一个是Bean的名字。可以对其做一些操作。

**注意：**后置处理器是针对所有Bean的，所以如果是想对某一个Bean创建前通过处理器做一些特殊操作，一定记得加一些判断条件，例如通过参数beanName做判断。

测试代码如下：

```java
@Component
public class LubanBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization");
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization");
        if ("userService".equals(beanName)){
            System.out.println(beanName);
            System.out.println(bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
```

**例子：**利用后置处理器来进行对象的属性赋值，以实现一个与Spring提供的@Value注解功能一样的注解。

利用注解标注一个Bean组件：

```java
@Component
public class UserService {
    @QinValue("qinxxx")
    private String name;
    public void test() {
        System.out.println(name);
    }
}
```

实现QinValue注解：

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QinValue {
    String value();
}
```

写后置处理器中对属性赋值的逻辑：

```java
@Component
public class QinBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();   // UserService.class
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(QinValue.class)) {
                QinValue fieldAnnotation = field.getAnnotation(QinValue.class);
                String value = fieldAnnotation.value();
                field.setAccessible(true);
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
```

容器中获得该Bean并调用对应的方法：

```java
AnnotationConfigApplicationContext applicationContext8 = new AnnotationConfigApplicationContext(Config.class);
UserService userService = applicationContext8.getBean("userService", UserService.class);
userService.test();
```





# Bug1：*--2022.2.21*

**问题描述：**启动项目之后，报错。

**错误信息：**错误信息如下：

```java
警告: Exception encountered during context initialization - cancelling refresh attempt:org.springframework.beans.factory.UnsatisfiedDependencyException:Error creating bean with name 'userDaoImpl':Unsatisfied dependency expressed through field 'jdbcTemplate';nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:No qualifying bean of type 'org.springframework.jdbc.core.JdbcTemplate' available:expected at least 1 bean which qualifies as autowire candidate.Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
```

**解决方法：**出现这个异常是有的方法上面没有写上@Bean注解，在相应的方法上面加上@Bean注解。



# Bug2：*--2022.2.22*

**问题描述：**启动项目之后，报错。

**错误信息：**错误信息如下：

```java
org.springframework.beans.factory.NoUniqueBeanDefinitionException: No qualifying bean of type 'org.springframework.transaction.TransactionManager' available: expected single matching bean but found 2: transactionManager,getDataSourceTransactionManager
```

**解决方法：**出现这个异常是对应的类上面写上了@Transactional注解，将该@Transactional注解去掉或者将配置类中的创建事务管理器方删除即可。



# Bug3：*--2022.7.31*

**问题描述：**使用AnnotationConfigApplicationContext类，然后启动之后，报错。

**错误信息：**错误信息如下：

```java
Exception in thread "main" java.lang.IllegalStateException: org.springframework.context.annotation.AnnotationConfigApplicationContext@1e3708 has not been refreshed yet
```

**解决方法：**出现这个异常是使用了AnnotationConfigApplicationContext类，调用了他的方法之后，没有使用他的refresh方法。