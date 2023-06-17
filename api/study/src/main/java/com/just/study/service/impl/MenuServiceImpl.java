package com.just.study.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.just.study.constant.UserConstant;
import com.just.study.entity.Menu;
import com.just.study.entity.MenuRole;
import com.just.study.entity.User;
import com.just.study.entity.UserRole;
import com.just.study.mapper.MenuMapper;
import com.just.study.service.IMenuRoleService;
import com.just.study.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.just.study.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单 服务实现类
 * </p>
 *
 * @author abc
 * @since 2023-02-24
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Resource
    private IUserRoleService userRoleService;

    @Resource
    private IMenuRoleService menuRoleService;

    //当前用户的菜单列表
    @Override
    public List<Menu> treeListUserMenuList(String token) {
        User user = this.getUser(token);
        if (user != null) {
            //admin
            if (user.getUserName().equals("admin")) {
                List<Menu> list = this.list();
                List<Menu> menus = this.getMenus(list, 0L);
                return menus;
            } else {
                //用户
                // 1 先查询当前用户关联的角色
                List<Long> roleIds = userRoleService.list(new QueryWrapper<UserRole>()
                                .eq("user_id", user.getUserId())).stream()
                        .map(UserRole::getRoleId).collect(Collectors.toList());
                // 2 再根据角色的权限
                List<Long> menuIds = menuRoleService.list(new QueryWrapper<MenuRole>()
                                .in("role_id", roleIds))
                        .stream().map(MenuRole::getMenuId).collect(Collectors.toList());
                // 3 查询菜单列表
                List<Menu> menus = this.list(new QueryWrapper<Menu>().in("menu_id", menuIds));
                //转换树形菜单
                List<Menu> menuList = this.getMenus(menus, 0L);
                return menuList;
            }
        }
        return null;
    }

    //树形菜单
    private List<Menu> getMenus(List<Menu> menus, Long parentId) {
        List<Menu> menuList = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    menu.setChildren(this.getMenus(menus, menu.getMenuId()));
                    return menu;
                }).collect(Collectors.toList());
        return menuList;
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
