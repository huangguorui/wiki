package com.jiawa.wiki.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
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
        return "hello world";
    }
}
