package com.bupt.wechatplugin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.bupt.wechatplugin.domain.*;


@SpringBootApplication  //等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
/*@PropertySource({"classpath:disconf.properties"})
@ImportResource({"classpath:disconf.xml"})//引入disconf*/
@MapperScan("com.bupt.wechatplugin.dao")
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
