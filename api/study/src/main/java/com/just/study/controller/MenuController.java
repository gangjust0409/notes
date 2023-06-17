package com.just.study.controller;


import com.just.study.entity.Menu;
import com.just.study.service.IMenuService;
import com.just.study.utils.Response;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author abc
 * @since 2023-02-24
 */
@CrossOrigin
@RestController
@RequestMapping("/study/menu")
public class MenuController {

    @Resource
    private IMenuService menuService;

    //当前用户的菜单列表
    @GetMapping("/tree/list")
    public Response treeListUserMenuList(@RequestHeader("X-Token") String token) {
        List<Menu> menus = menuService.treeListUserMenuList(token);
        return Response.success(menus);
    }

}
