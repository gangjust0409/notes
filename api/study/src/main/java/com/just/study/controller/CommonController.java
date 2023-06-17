package com.just.study.controller;


import com.just.study.entity.Common;
import com.just.study.service.ICommonService;
import com.just.study.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

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
@RequestMapping("/study/common")
public class CommonController {

    @Autowired
    private ICommonService commonService;

    //评论
    @PostMapping("")
    public Response add(@RequestBody Common common, @RequestHeader("X-Token") String token) {
        commonService.common(common, token);
        return Response.success();
    }

    //当前文章下的评论
    @GetMapping("/article/{articleId}")
    public Response list(@PathVariable Long articleId, @RequestHeader(value = "X-Token", required = false) String token) {
        List<Common> commons = commonService.list(articleId, token);
        return Response.success(commons);
    }

    //删除评论
    @DeleteMapping("{commonId}")
    public Response delete(@PathVariable Long commonId) {
        commonService.deleteCommon(commonId);
        return Response.success();
    }

}
