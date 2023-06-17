package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@TableName("article_link")
public class ArticleLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "al_id", type = IdType.AUTO)
    private Long alId;

    /**
     * 文章 id
     */
    private Long articleId;

    /**
     * 1 收藏 2 喜欢 3 点赞
     */
    private Integer type;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    private Long userId;

    public Long getAlId() {
        return alId;
    }

    public void setAlId(Long alId) {
        this.alId = alId;
    }
    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ArticleLink{" +
            "alId=" + alId +
            ", articleId=" + articleId +
            ", type=" + type +
            ", status=" + status +
            ", userId=" + userId +
        "}";
    }
}
