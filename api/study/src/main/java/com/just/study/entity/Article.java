package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "article_id", type = IdType.AUTO)
    private Long articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 当前文章关联的用户 id
     */
    private Long userId;

    /**
     * 0 待审核 1 我的作品 2 收藏 3 喜欢 4 点赞
     */
    private Integer type;

    /**
     * 阅读量
     */
    private Long reading;

    /**
     * 评论量
     */
    private Long common;

    /**
     * 点赞量
     */
    private Long love;

    /**
     * 文章创建时间
     */
    private LocalDate createdDate;

    /**
     * 文章图片
     */
    private String img;

    /**
     * 当前文章热度是否推荐到首页
     */
    private Long heat;

    /**
     * md 文档内容
     */
    private String content;

    /**
     * 分类 id
     */
    private Long catalogId;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public Long getReading() {
        return reading;
    }

    public void setReading(Long reading) {
        this.reading = reading;
    }
    public Long getCommon() {
        return common;
    }

    public void setCommon(Long common) {
        this.common = common;
    }
    public Long getLove() {
        return love;
    }

    public void setLove(Long love) {
        this.love = love;
    }
    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public Long getHeat() {
        return heat;
    }

    public void setHeat(Long heat) {
        this.heat = heat;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Article{" +
            "articleId=" + articleId +
            ", title=" + title +
            ", userId=" + userId +
            ", type=" + type +
            ", reading=" + reading +
            ", common=" + common +
            ", love=" + love +
            ", createdDate=" + createdDate +
            ", img=" + img +
            ", heat=" + heat +
            ", content=" + content +
            ", catalogId=" + catalogId +
            ", status=" + status +
        "}";
    }
}
