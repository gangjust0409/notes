package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.just.study.constant.UserConstant;
import com.just.study.entity.User;
import com.just.study.entity.UserCorrelation;
import com.just.study.interceptor.LoginInterceptor;
import com.just.study.mapper.UserCorrelationMapper;
import com.just.study.service.IUserCorrelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-14
 */
@Service
public class UserCorrelationServiceImpl extends ServiceImpl<UserCorrelationMapper, UserCorrelation> implements IUserCorrelationService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Boolean validUserIsCurrentAttention(Long userId, String token) {
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        UserCorrelation userCorrelation = this.getOne(new QueryWrapper<UserCorrelation>().eq("current_user_id", user.getUserId())
                .eq("attention_user_id", userId));
        if (userCorrelation == null) return false;
        return true;
    }

    @Override
    public void saveUserCorrelation(UserCorrelation userCorrelation, String token) {
        userCorrelation.setCreatedDate(LocalDateTime.now());
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        userCorrelation.setCurrentUserId(user.getUserId());
        List<UserCorrelation> list = this.list(new QueryWrapper<UserCorrelation>().eq("current_user_id", user.getUserId())
                .eq("attention_user_id", userCorrelation.getAttentionUserId()));
        if (list == null || list.size() == 0) {
            this.save(userCorrelation);
        }
    }

    @Override
    public void canelUserCorrelation(Long userId, String token) {
        //User user = LoginInterceptor.currentUser.get();
        User user = this.getUser(token);
        this.remove(new QueryWrapper<UserCorrelation>().eq("current_user_id", user.getUserId())
                .eq("attention_user_id", userId));
    }

    @Override
    public User queryFollowAndFans(User user) {
        long follow = this.count(new QueryWrapper<UserCorrelation>().eq("attention_user_id", user.getUserId()));
        long fans = this.count(new QueryWrapper<UserCorrelation>().eq("current_user_id", user.getUserId()));
        user.setFollow(follow);
        user.setFans(fans);
        return user;
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
