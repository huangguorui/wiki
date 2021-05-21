package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Ebook;
import com.jiawa.wiki.domain.EbookExample;
import com.jiawa.wiki.mapper.EbookMapper;
import com.jiawa.wiki.req.EbookQueryReq;
import com.jiawa.wiki.req.EbookSaveReq;
import com.jiawa.wiki.resp.EbookQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
import com.jiawa.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);


    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {

        PageHelper.startPage(req.getPage(),req.getSize());

        //Example 相当于where条件 不传值可以设置为null或者new EbookExample()
        //ctrl+alt+v 快速生成变量
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        //左匹配和右匹配
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        pageInfo.getTotal();
        pageInfo.getPages();
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<EbookQueryResp> respList = new ArrayList<>();
        //iter
//        for (Ebook ebook : ebookList) {
//            //EbookQueryResp ebookResp = new EbookQueryResp();
//            //BeanUtils.copyProperties(ebook,ebookResp);
//            //对象复制
//            EbookQueryResp ebookResp = CopyUtil.copy(ebook, EbookQueryResp.class);
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     * @param req
     */
    public void save(EbookSaveReq req){
        //复制
        Ebook ebook = CopyUtil.copy(req,Ebook.class);

        if (ObjectUtils.isEmpty(req.getId())){
            //TODO 使用雪花算法之后，传给前端的数据id和数据库对应的id值没有对应，暂时13位数的时间戳替代
            // ebook.setId(snowFlake.nextId());
            // UUID uuid = UUID.randomUUID();
            // String id = uuid.toString();
            long  timeNew =  System.currentTimeMillis(); // 13位数的时间戳
            ebook.setId(timeNew);
            //为空新增
            ebookMapper.insert(ebook);
        }else {
            //更新
            ebookMapper.updateByPrimaryKey(ebook);
        }
    }

    public void delete(Long id){
        //根据主键删除
        ebookMapper.deleteByPrimaryKey(id);
    }
}
