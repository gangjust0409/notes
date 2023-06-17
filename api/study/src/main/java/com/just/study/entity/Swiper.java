package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
public class Swiper implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "swiper_id", type = IdType.AUTO)
    private Long swiperId;

    /**
     * 文章 id
     */
    private Long articleId;

    /**
     * 图片地址
     */
    private String img;

    /**
     * 0 启用 1 禁用
     */
    private Integer enable;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    public Long getSwiperId() {
        return swiperId;
    }

    public void setSwiperId(Long swiperId) {
        this.swiperId = swiperId;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Swiper{" +
            "swiperId=" + swiperId +
            ", articleId=" + articleId +
            ", img=" + img +
            ", enable=" + enable +
            ", status=" + status +
        "}";
    }
}
