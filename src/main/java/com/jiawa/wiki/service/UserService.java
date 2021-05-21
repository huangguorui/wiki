package com.jiawa.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jiawa.wiki.domain.User;
import com.jiawa.wiki.domain.UserExample;
import com.jiawa.wiki.exception.BusinessException;
import com.jiawa.wiki.exception.BusinessExceptionCode;
import com.jiawa.wiki.mapper.UserMapper;
import com.jiawa.wiki.req.UserQueryReq;
import com.jiawa.wiki.req.UserSaveReq;
import com.jiawa.wiki.resp.UserQueryResp;
import com.jiawa.wiki.resp.PageResp;
import com.jiawa.wiki.util.CopyUtil;
import com.jiawa.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);


    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req) {

        PageHelper.startPage(req.getPage(),req.getSize());

        //Example 相当于where条件 不传值可以设置为null或者new UserExample()
        //ctrl+alt+v 快速生成变量
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //左匹配和右匹配
        if(!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andNameLike("%" + req.getLoginName() + "%");
        }

        List<User> userList = userMapper.selectByExample(userExample);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        pageInfo.getTotal();
        pageInfo.getPages();
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());

//        List<UserQueryResp> respList = new ArrayList<>();
        //iter
//        for (User user : userList) {
//            //UserQueryResp userResp = new UserQueryResp();
//            //BeanUtils.copyProperties(user,userResp);
//            //对象复制
//            UserQueryResp userResp = CopyUtil.copy(user, UserQueryResp.class);
//            respList.add(userResp);
//        }

        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);

        return pageResp;
    }

    /**
     * 保存
     * @param req
     */
    public void save(UserSaveReq req){
        //复制
        User user = CopyUtil.copy(req,User.class);

        if (ObjectUtils.isEmpty(req.getId())){
            User userDB = selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                //TODO 使用雪花算法之后，传给前端的数据id和数据库对应的id值没有对应，暂时13位数的时间戳替代
                // user.setId(snowFlake.nextId());
                // UUID uuid = UUID.randomUUID();
                // String id = uuid.toString();
                long  timeNew =  System.currentTimeMillis(); // 13位数的时间戳
                user.setId(timeNew);
                //为空新增
                userMapper.insert(user);
            }else {
                //用户名已存在
                throw new BusinessException((BusinessExceptionCode.USER_LOGIN_NAME_EXIST));
            }

        }else {
            //更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id){
        //根据主键删除
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)){
            return null;
        }else {
          return   userList.get(0);
        }
    }
}
