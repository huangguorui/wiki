package com.jiawa.wiki.controller;

import com.jiawa.wiki.req.EbookReq;
import com.jiawa.wiki.req.EbookSaveReq;
import com.jiawa.wiki.resp.CommonResp;
import com.jiawa.wiki.resp.EbookQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        //ctrl+alt+v 快速生成一个变量
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp save(@RequestBody EbookSaveReq req){
        //ctrl+alt+v 快速生成一个变量
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        //ctrl+alt+v 快速生成一个变量
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }

}
