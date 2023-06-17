package com.just.study.controller;


import com.just.study.entity.UserCorrelation;
import com.just.study.service.IUserCorrelationService;
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
 * @since 2023-02-14
 */
@CrossOrigin
@RestController
@RequestMapping("/study/user-correlation")
public class UserCorrelationController {

    @Autowired
    private IUserCorrelationService userCorrelationService;

    //关注用户
    @PostMapping
    public Response add(@RequestBody UserCorrelation userCorrelation, @RequestHeader("X-Token") String token) {
        userCorrelationService.saveUserCorrelation(userCorrelation, token);
        return Response.success();
    }

    //取消关注
    @DeleteMapping("{userId}")
    public Response del(@PathVariable Long userId, @RequestHeader("X-Token") String token) {
        userCorrelationService.canelUserCorrelation(userId, token);
        return Response.success();
    }
}
