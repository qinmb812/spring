package com.at.spring5.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration  // 配置类
@ComponentScan(basePackages = {"com.at.spring5"})   // 组件扫描
@EnableTransactionManagement    // 开启事务
public class TxConfig {
    // 创建数据库连接池
    @Bean
    public DruidDataSource getDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql:///user_db");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    // 创建JdbcTemplate对象
    @Bean
    public JdbcTemplate getJdbcTemplate(DataSource dataSource) {    // 到IOC容器中根据类型找到dataSource
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        // 注入dataSource
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    // 创建事务管理器
//    @Bean
//    public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource) {
//        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
//        transactionManager.setDataSource(dataSource);
//        return transactionManager;
//    }
}
/**
 * 警告: Exception encountered during context initialization - cancelling refresh attempt:
 *      org.springframework.beans.factory.UnsatisfiedDependencyException:
 *          Error creating bean with name 'userDaoImpl':
 *              Unsatisfied dependency expressed through field 'jdbcTemplate';
 *              nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException:
 *                  No qualifying bean of type 'org.springframework.jdbc.core.JdbcTemplate' available:
 *                      expected at least 1 bean which qualifies as autowire candidate.
 *                      Dependency annotations: {@org.springframework.beans.factory.annotation.Autowired(required=true)}
 *
 * 出现这个错误是有的方法上面没有写上@Bean注解
 */