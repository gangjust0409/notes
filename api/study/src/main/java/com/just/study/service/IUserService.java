package com.just.study.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.just.study.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
public interface IUserService extends IService<User> {

    /**
     * 登录
     * @param user
     * @return
     */
    String login(User user);

    /**
     * 令牌换取用户信息
     * @param token
     * @return
     */
    User tokenAnalysisUser(String token);

    /**
     * 用户搜索
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param token
     * @return
     */
    Page<User> searchUsers(String keyword, Integer pageNum, Integer pageSize, String token);

    /**
     * 查看其它用户
     *
     * @param userId
     * @param token
     * @return
     */
    User otherUser(Long userId, String token);

    /**
     * 注册用户
     * @param user
     */
    void register(User user);

    /**
     * 退出登录
     * @param token
     */
    void loginOut(String token);

    /**
     * 验证唯一
     * @param name
     * @return
     */
    int validUnique(String name);
}
