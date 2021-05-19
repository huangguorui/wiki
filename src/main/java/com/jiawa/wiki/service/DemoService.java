package com.jiawa.wiki.service;

import com.jiawa.wiki.domain.Demo;
import com.jiawa.wiki.domain.DemoExample;
import com.jiawa.wiki.mapper.DemoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DemoService {

//    @Resource //jdk自带
//    @Autowired //Spring
    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){
        //Example 相当于where条件 不传值可以设置为null或者new DemoExample()
        return demoMapper.selectByExample(null);
    }
}
