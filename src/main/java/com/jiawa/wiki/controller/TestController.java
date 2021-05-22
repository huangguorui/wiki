package com.jiawa.wiki.controller;

import com.jiawa.wiki.domain.Test;
import com.jiawa.wiki.service.TestService;
import com.jiawa.wiki.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private TestService testService;

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);


    @Resource
    private RedisTemplate redisTemplate;

    /**
     * GET,POST,PUT,DELETE  RESTFUL，是一种风格，约定。
     * GET发送一个查询的请求
     * POST新增
     * PUT修改
     * DELETE删除
     * 传统风格
     * /user?id=1
     * RESTFUL风格
     * /user/1
     * @return
     */
    @RequestMapping("/hello")
    public String hello(){
        return "Hello World"+testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){

        return "Hello World Post : "+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key: {}, value: {}", key, value);
        return "success";
    }

//    @RequestMapping("/redis/get/{key}")
//    public Object get(@PathVariable Long key) {
//        Object object = redisTemplate.opsForValue().get(key);
//        LOG.info("key: {}, value: {}", key, object);
//        return object;
//    }
    @RequestMapping("/redis/get/{key}")
    public Object get(@PathVariable String key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key: {}, value: {}", key, object);
        return object;
    }


}
