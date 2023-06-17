package com.just.study.controller;


import com.just.study.service.IFileConvertAddService;
import com.just.study.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

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
@RequestMapping("/study/add-convert-file")
public class FileConvertAddController {

    @Autowired
    private IFileConvertAddService fileConvertAddService;

    //上传用户头像
    @PostMapping("/upload")
    public Response articleUploadFile(@RequestPart("avatar") MultipartFile multipartFile,
                                      @RequestHeader("X-Token") String token) {
        int res = fileConvertAddService.handlerUploadArticleImg(multipartFile, token);
        return Response.success(res);
    }

    //上传作为笔记中的图片
    @PostMapping("/mulit/upload")
    public Response uploadMultipartFiles(@RequestPart("file") MultipartFile [] multipartFiles,@RequestHeader("X-Token") String token) {
        int res = fileConvertAddService.uploadMultipartFiles(multipartFiles, token);
        return Response.success(res);
    }

    //展示作为笔记中的图片
    @GetMapping("/upload/pre")
    public Response uploadPreview(@RequestHeader("X-Token") String token) {
        Set<String> urls = fileConvertAddService.uploadPreview(token);
        return Response.success(urls);
    }

    //删除展示作为笔记中的图片
    @DeleteMapping("{fileName}")
    public Response deleteFile(@PathVariable String fileName, @RequestHeader("X-Token") String token) {
        fileConvertAddService.deleteFile(fileName, token);
        return Response.success();
    }

    //后台：笔记封面上传
    @PostMapping("/upload/article")
    public Response uploadArticleImg(@RequestPart("file") MultipartFile file,
                                     @RequestHeader("X-Token") String token) {
        fileConvertAddService.uploadArticleImage(file, token);
        return Response.success();
    }

}
