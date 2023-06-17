package com.just.study.service;

import com.just.study.entity.FileConvertAdd;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-09
 */
public interface IFileConvertAddService extends IService<FileConvertAdd> {

    /**
     * 上传用户头像
     *
     * @param multipartFile
     * @param token
     * @return
     */
    int handlerUploadArticleImg(MultipartFile multipartFile, String token);

    /**
     * 上传多张图片
     *
     * @param multipartFiles
     * @param token
     * @return
     */
    int uploadMultipartFiles(MultipartFile[] multipartFiles, String token);

    /**
     * 展示作为笔记中的图片
     * @return
     */
    Set<String> uploadPreview(String token);

    /**
     * 删除展示作为笔记中的图片
     *
     * @param fileName
     * @param token
     */
    void deleteFile(String fileName, String token);

    /**
     * 上传笔记图片
     *
     * @param file
     * @param token
     */
    void uploadArticleImage(MultipartFile file, String token);
}
