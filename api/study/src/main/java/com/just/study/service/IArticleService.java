package com.just.study.service;

import com.just.study.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.just.study.vo.ArticleDetailVo;
import com.just.study.vo.ArticleParamVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-09
 */
public interface IArticleService extends IService<Article> {

    /**
     * 添加文章
     *
     * @param article
     * @param token
     */
    void addArticle(Article article, String token);

    /**
     * 根据 用户id 获取文章
     *
     * @param type
     * @param num
     * @param size
     * @param token
     * @return
     */
    Page<Article> userIdGetArticleList(Integer type, Integer num, Integer size, String token);

    /**
     * 详情页展示
     *
     * @param id
     * @param token
     * @return
     */
    ArticleDetailVo articleGetById(Long id, String token);

    /**
     * 每次访问度
     * @param articleId
     */
    void countRecommend(Long articleId);

    /**
     *首页推荐列表
     * @return
     */
    Page<Article> recommendList(Integer pageNum, Integer pageSize);

    /**
     * 搜索
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<Article> searchArticles(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 查看别人的笔记
     * @param userId
     * @return
     */
    Page<Article> otherUserArticles(Long userId);

    /**
     * 删除文章
     * @param articleId
     */
    void del(Long articleId);

    /**
     * 关注列表
     * @return
     */
    Page<Article> attentionList(Integer pageNum, Integer pageSize, String token);

    /**
     * 后台管理，文章列表
     * @param paramVo
     * @return
     */
    Page<Article> backstrageArticles(ArticleParamVo paramVo);

    /**
     * 审核
     *
     * @param articleId
     * @param token
     */
    void auditArticle(Long articleId, String token);
}
