package com.just.study.controller;


import com.just.study.entity.Article;
import com.just.study.service.IArticleService;
import com.just.study.utils.Response;
import com.just.study.vo.ArticleDetailVo;
import com.just.study.vo.ArticleParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author abc
 * @since 2023-02-09
 */
@CrossOrigin
@RestController
@RequestMapping("/study/article")
public class ArticleController {

    @Autowired
    private IArticleService articleService;

    //查询全部文章
    @GetMapping
    public Response list() {
        List<Article> list = articleService.list();
        return Response.success(list);
    }

    //发布文章
    @PostMapping("")
    public Response add(@RequestBody Article article,
                        @RequestHeader("X-Token") String token) {
        articleService.addArticle(article, token);
        return Response.success();
    }

    //根据当前用户的文章
    @GetMapping("/get/{type}")
    public Response userIdGetArticle(@PathVariable Integer type, @RequestParam Integer num, @RequestParam Integer size,
                                     @RequestHeader("X-Token") String token) {
        Page<Article> articles = articleService.userIdGetArticleList(type, num, size, token);
        return Response.success(articles);
    }

    //详情页展示
    @GetMapping("{id}")
    public Response articleById(@PathVariable Long id,@RequestHeader(value = "X-Token", required = false) String token) {
        ArticleDetailVo article = articleService.articleGetById(id, token);
        return Response.success(article);
    }

    //计算每次访问的文章
    @GetMapping("/recommend/{articleId}")
    public Response countRecommend(@PathVariable Long articleId) {
        articleService.countRecommend(articleId);
        return Response.success();
    }

    //首页推荐列表
    @GetMapping("/recommend/list")
    public Response recommendList(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<Article> articles = articleService.recommendList(pageNum, pageSize);
        return Response.success(articles);
    }

    //搜索
    @GetMapping("/search")
    public Response searchArticles(@RequestParam String keyword, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        Page<Article> page = articleService.searchArticles(keyword, pageNum, pageSize);
        return Response.success(page);
    }

    // 查看别的用户笔记
    @GetMapping("/other/user/article")
    public Response otherUserArticles(@RequestParam Long userId) {
        Page<Article> page = articleService.otherUserArticles(userId);
        return Response.success(page);
    }

    //删除文章
    @DeleteMapping("{articleId}")
    public Response del(@PathVariable Long articleId) {
        articleService.del(articleId);
        return Response.success();
    }

    //关注列表
    @GetMapping("/attention/list")
    public Response attentionList(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
                                  @RequestHeader(value = "X-Token", required = false) String token) {
        Page<Article> attentionList = articleService.attentionList(pageNum, pageSize, token);
        return Response.success(attentionList);
    }

    //后台管理系统，查询笔记
    @GetMapping("/backstage")
    public Response articleListFun(ArticleParamVo paramVo) {
        Page<Article> page = articleService.backstrageArticles(paramVo);
        return Response.success(page);
    }

    //后台管理系统 审核
    @GetMapping("/audit/{articleId}")
    public Response auditArticle(@PathVariable Long articleId, @RequestHeader("X-Token") String token) {
        articleService.auditArticle(articleId, token);
        return Response.success();
    }

}
