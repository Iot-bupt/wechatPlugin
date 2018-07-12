package com.bupt.wechatplugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
/*@PropertySource({"classpath:disconf.properties"})
@ImportResource({"classpath:disconf.xml"})//引入disconf*/
public class Application {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
