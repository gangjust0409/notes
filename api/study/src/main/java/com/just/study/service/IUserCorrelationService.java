package com.just.study.service;

import com.just.study.entity.User;
import com.just.study.entity.UserCorrelation;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-14
 */
public interface IUserCorrelationService extends IService<UserCorrelation> {

    /**
     * 是否关注该用户
     *
     * @param userId
     * @param token
     * @return
     */
    Boolean validUserIsCurrentAttention(Long userId, String token);

    /**
     * 关注用户
     *
     * @param userCorrelation
     * @param token
     */
    void saveUserCorrelation(UserCorrelation userCorrelation, String token);

    /**
     * 取消关注
     *
     * @param userId
     * @param token
     */
    void canelUserCorrelation(Long userId, String token);

    /**
     * 查询关注和粉丝
     * @param user
     * @return
     */
    User queryFollowAndFans(User user);
}
