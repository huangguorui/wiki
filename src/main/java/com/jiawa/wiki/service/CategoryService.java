package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.Category;
import com.jiawa.wiki.domain.CategoryExample;
import com.jiawa.wiki.mapper.CategoryMapper;
import com.jiawa.wiki.req.CategoryQueryReq;
import com.jiawa.wiki.req.CategorySaveReq;
import com.jiawa.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);


    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {

        PageHelper.startPage(req.getPage(),req.getSize());

        //Example 相当于where条件 不传值可以设置为null或者new CategoryExample()
        //ctrl+alt+v 快速生成变量
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
//        //左匹配和右匹配
//        if(!ObjectUtils.isEmpty(req.getName())){
//            criteria.andNameLike("%" + req.getName() + "%");
//        }

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        pageInfo.getTotal();
        pageInfo.getPages();
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<CategoryQueryResp> respList = new ArrayList<>();
        //iter
//        for (Category category : categoryList) {
//            //CategoryQueryResp categoryResp = new CategoryQueryResp();
//            //BeanUtils.copyProperties(category,categoryResp);
//            //对象复制
//            CategoryQueryResp categoryResp = CopyUtil.copy(category, CategoryQueryResp.class);
//            respList.add(categoryResp);
//        }

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }
    public List<CategoryQueryResp> all() {

        CategoryExample categoryExample = new CategoryExample();

        //排序
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return list;
    }

    /**
     * 保存
     * @param req
     */
    public void save(CategorySaveReq req){
        //复制
        Category category = CopyUtil.copy(req,Category.class);

        if (ObjectUtils.isEmpty(req.getId())){
            //TODO 使用雪花算法之后，传给前端的数据id和数据库对应的id值没有对应，暂时13位数的时间戳替代
            // category.setId(snowFlake.nextId());
            // UUID uuid = UUID.randomUUID();
            // String id = uuid.toString();
            long  timeNew =  System.currentTimeMillis(); // 13位数的时间戳
            category.setId(timeNew);
            //为空新增
            categoryMapper.insert(category);
        }else {
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id){
        //根据主键删除
        categoryMapper.deleteByPrimaryKey(id);
    }
}
