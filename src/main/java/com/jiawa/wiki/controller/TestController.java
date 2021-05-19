package com.jiawa.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TestController {

    @Value("${test.hello:TEST}")
    private String testHello;


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
}
