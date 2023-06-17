package com.just.study.interceptor;

import com.alibaba.fastjson2.JSON;
import com.just.study.entity.User;
import com.just.study.utils.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private Log log = Log.getLogger(LoginInterceptor.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 当前登录的用户
     */
    public static ThreadLocal<User> currentUser = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Cookie[] cookies = request.getCookies();
        if(cookies != null ){
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String json = redisTemplate.opsForValue().get("user:" + cookie.getValue());
                    User user = JSON.parseObject(json, User.class);
                    currentUser.set(user);
                    System.out.println("打印" + currentUser.get());
                }
            }
        }
        return true;
    }
}
