# spring
Spring学习



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