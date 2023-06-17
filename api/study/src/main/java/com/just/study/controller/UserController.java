package com.just.study.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.just.study.constant.FileConstant;
import com.just.study.entity.Article;
import com.just.study.entity.User;
import com.just.study.service.IUserService;
import com.just.study.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@CrossOrigin
@RestController
@RequestMapping("/study/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody User user) {
        String token = userService.login(user);
        return Response.success(token);
    }

    @GetMapping("/user/info/{token}")
    public Response getUserInfo(@PathVariable String token) {
        User user = userService.tokenAnalysisUser(token);
        return Response.success(user);
    }

    //用户搜索
    @GetMapping("/search")
    public Response searchUser(@RequestParam String keyword, @RequestParam Integer pageNum, @RequestParam Integer pageSize,
                               @RequestHeader("X-Token") String token) {
        Page<User> page = userService.searchUsers(keyword, pageNum, pageSize, token);
        return Response.success(page);
    }

    //点击查看用户
    @GetMapping("/other/user/{userId}")
    public Response getUser(@PathVariable Long userId,@RequestHeader("X-Token") String token) {
        User byId = userService.otherUser(userId, token);
        return Response.success(byId);
    }

    //注册

    /**
     *docker run -p 80:80 --name nginx \
      -v /http/nginx/html:/usr/share/nginx/html \
      -v /http/nginx/logs:/var/log/nginx \
      -v /http/nginx/conf:/etc/nginx \
      -d nginx:1.10
     * @param user
     * @return
     */
    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        userService.register(user);
        return Response.success();
    }

    //退出登录
    @GetMapping("/loginOut")
    public Response loginOut(@RequestHeader("X-Token") String token) {
        userService.loginOut(token);
        return Response.success();
    }

    //验证唯一性
    @GetMapping("/unique/{name}")
    public Response validUnique(@PathVariable String name) {
        int res = userService.validUnique(name);
        return Response.success(res);
    }

}
