package com.just.study.service;

import com.just.study.entity.ArticleLink;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
public interface IArticleLinkService extends IService<ArticleLink> {

    /**
     * 收藏、喜欢、点赞
     *
     * @param articleLink
     * @param token
     */
    void setting(ArticleLink articleLink, String token);

    /**
     * 喜欢点赞收藏
     * @param text
     */
    void delete(String text);
}
