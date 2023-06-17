package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.just.study.constant.UserConstant;
import com.just.study.entity.ArticleLink;
import com.just.study.entity.User;
import com.just.study.interceptor.LoginInterceptor;
import com.just.study.mapper.ArticleLinkMapper;
import com.just.study.service.IArticleLinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@Service
public class ArticleLinkServiceImpl extends ServiceImpl<ArticleLinkMapper, ArticleLink> implements IArticleLinkService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void setting(ArticleLink articleLink, String token) {
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        articleLink.setUserId(user.getUserId());
        articleLink.setStatus(1);
        this.save(articleLink);
    }

    @Override
    public void delete(String text) {
        String[] ids = text.split("-");
        QueryWrapper<ArticleLink> articleLinkQueryWrapper = new QueryWrapper<ArticleLink>().eq("article_id", ids[0]).eq("type", ids[1]);
        this.remove(articleLinkQueryWrapper);
    }

    /**
     * 获取当前用户
     * @param token
     * @return
     */
    private User getUser(String token) {
        String val = redisTemplate.opsForValue().get(UserConstant.CURRENT_LOGIN_USER + token);
        User user = JSON.parseObject(val, User.class);
        return user;
    }
}
