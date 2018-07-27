package com.bupt.wechatplugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.bupt.wechatplugin.domain.*;


@SpringBootApplication  //等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
/*@PropertySource({"classpath:disconf.properties"})
@ImportResource({"classpath:disconf.xml"})//引入disconf*/
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
