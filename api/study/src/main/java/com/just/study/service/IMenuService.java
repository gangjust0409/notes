package com.just.study.service;

import com.just.study.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单 服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-24
 */
public interface IMenuService extends IService<Menu> {

    /**
     * 当前用户的菜单列表
     * @param token
     * @return
     */
    List<Menu> treeListUserMenuList(String token);
}
