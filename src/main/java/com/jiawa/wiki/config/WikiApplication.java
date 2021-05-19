package com.jiawa.wiki.config;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

//@ComponentScan({"com.jiawa","com.test"})
@ComponentScan("com.jiawa")
@SpringBootApplication
//扫描mapper包
@MapperScan("com.jiawa.wiki.mapper")
public class WikiApplication {
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(WikiApplication.class);
        //打印自定义配置信息，打印欢迎页，测试地址，或者提示性方案等等。
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
