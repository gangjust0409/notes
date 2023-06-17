package com.just.study.service;

import com.just.study.entity.Common;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
public interface ICommonService extends IService<Common> {

    /**
     * 当前文章下的评论
     *
     * @param articleId
     * @param token
     * @return
     */
    List<Common> list(Long articleId, String token);

    /**
     * 评论
     *
     * @param common
     * @param token
     */
    void common(Common common, String token);

    /**
     * 删除评论
     * @param commonId
     */
    void deleteCommon(Long commonId);
}
