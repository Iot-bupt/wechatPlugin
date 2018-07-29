//package com.bupt.wechatplugin.config;
//
//import lombok.Data;
//import org.apache.ibatis.mapping.Environment;
//import org.mybatis.spring.annotation.MapperScan;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.EnvironmentAware;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//import javax.sql.DataSource;
//import java.util.Properties;
//
//
///**
// *  配置数据源
// */
//@Data
//@EnableAutoConfiguration
//public class MybatisConfig {
//    private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);
//
//    @Autowired
//    private ComboPooledDataSource dataSource;
//
//    @Override
//    public void setEnvironment(Environment environment) {
//        this.env = environment;
//    }
//
//    /**
//     * 创建数据源
//     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
//     */
//    @Bean
//    @Primary
//    public DataSource getDataSource() throws Exception{
//        Properties props = new Properties();
//        props.put("driverClassName", env.getProperty("spring.datasource.driver-class-name"));
//        props.put("url", env.getProperty("spring.datasource.url"));
//        props.put("username", env.getProperty("spring.datasource.username"));
//        props.put("password", env.getProperty("spring.datasource.password"));
//        return DruidDataSourceFactory.createDataSource(props);
//    }
//}
