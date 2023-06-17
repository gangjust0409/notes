package com.just.study.controller;


import com.just.study.entity.ArticleLink;
import com.just.study.service.IArticleLinkService;
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
@RequestMapping("/study/article-link")
public class ArticleLinkController {

    @Autowired
    private IArticleLinkService articleLinkService;

    @PostMapping("/setting")
    public Response setting(@RequestBody ArticleLink articleLink,
                            @RequestHeader("X-Token") String token) {
        articleLinkService.setting(articleLink, token);
        return Response.success();
    }

    @DeleteMapping("{text}")
    public Response settingDelete(@PathVariable String text) {
        articleLinkService.delete(text);
        return Response.success();
    }

}
