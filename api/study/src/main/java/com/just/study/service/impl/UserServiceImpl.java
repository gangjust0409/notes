package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.just.study.constant.FileConstant;
import com.just.study.constant.UserConstant;
import com.just.study.entity.User;
import com.just.study.interceptor.LoginInterceptor;
import com.just.study.mapper.UserMapper;
import com.just.study.service.IUserCorrelationService;
import com.just.study.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.just.study.utils.Log;
import com.just.study.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private Log log = Log.getLogger(this.getClass());

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private IUserCorrelationService userCorrelationService;

    @Override
    public String login(User user) {
        User u = this.getOne(new QueryWrapper<User>().eq("user_name", user.getUserName()));
        log.info(user.getPassword() + "\t"+ u.getPassword().equals(user.getPassword()));
        if (user.getPassword() == null || !u.getPassword().equals(user.getPassword())) return null;
        String uuid = StringUtils.randomUUID();
        redisTemplate.opsForValue().set(UserConstant.CURRENT_LOGIN_USER + uuid, JSON.toJSONString(u), 1, TimeUnit.DAYS);
        return uuid;
    }

    @Override
    public User tokenAnalysisUser(String token) {
        String json = redisTemplate.opsForValue().get(UserConstant.CURRENT_LOGIN_USER + token);
        if (json == null) return null;
        User user = JSON.parseObject(json, User.class);
        user.setPic(user.getPic() == null ? null : FileConstant.SERVER_FILE_UPLOAD_PATH + user.getPic());
        //查询关注和粉丝
        User u = userCorrelationService.queryFollowAndFans(user);
        return u;
    }

    @Override
    public Page<User> searchUsers(String keyword, Integer pageNum, Integer pageSize, String token) {
        Page<User> page = Page.of(pageNum, pageSize);
        //User u = LoginInterceptor.currentUser.get();
        User u = this.getUser(token);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        try {
            userQueryWrapper.eq("user_id", Integer.parseInt(keyword));
        }catch (NumberFormatException e) {
            userQueryWrapper.like("user_name", keyword);
        }
        Page<User> userPage = baseMapper.selectPage(page, userQueryWrapper);
        List<User> userList = userPage.getRecords().stream().map(user -> {
            user.setPic(user.getPic() == null ? null : FileConstant.SERVER_FILE_UPLOAD_PATH + user.getPic());
            user.getUserName().replace(keyword, "<b style='color:red;'>" + keyword + "</b>");
            //查询用户与当前用户是否有关联
            Boolean attention = userCorrelationService.validUserIsCurrentAttention(user.getUserId(), token);
            user.setAttention(attention);
            if (u.getUserId().equals(user.getUserId())) user.setAttention(null);
            return user;
        }).collect(Collectors.toList());
        //重新设置进去
        userPage.setRecords(userList);

        return userPage;
    }

    @Override
    public User otherUser(Long userId, String token) {
        User user = this.getById(userId);
        //User usr = LoginInterceptor.currentUser.get();
        User usr = this.getUser(token);
        user.setPic(user.getPic() == null ? null : FileConstant.SERVER_FILE_UPLOAD_PATH + user.getPic());
        //查询是否关注该用户
        boolean flag = userCorrelationService.validUserIsCurrentAttention(userId, token);
        user.setAttention(flag);
        if (usr.getUserId().equals(user.getUserId())) user.setAttention(null);
        //设置用户关注和粉丝
        User u = userCorrelationService.queryFollowAndFans(user);
        return u;
    }

    @Override
    public void register(User user) {
        this.save(user);
    }

    @Override
    public void loginOut(String token) {
        redisTemplate.delete(UserConstant.CURRENT_LOGIN_USER + token);
    }

    @Override
    public int validUnique(String name) {
        User user = this.getOne(new QueryWrapper<User>().eq("user_name", name));
        if (user == null) return 0;
        return 1;
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
