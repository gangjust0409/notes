package com.just.study.vo;

import com.just.study.entity.Article;
import com.just.study.entity.Common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class ArticleDetailVo  {

    /**
     * 文章 id
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 发布日期
     */
    private LocalDate releaseDate;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否喜欢
     */
    private boolean isLove;

    /**
     * 是否点赞
     */
    private boolean isGiveLike;

    /**
     * 是否收藏
     */
    private boolean isCollect;

    /**
     * 评论列表
     */
    private List<Common> commons;

    /**
     * 该作者的其他文章
     */
    private List<Article> otherArticles;

    private boolean isDelete;

    public boolean isLove() {
        return isLove;
    }

    public void setLove(boolean love) {
        isLove = love;
    }

    public boolean isGiveLike() {
        return isGiveLike;
    }

    public void setGiveLike(boolean giveLike) {
        isGiveLike = giveLike;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }
}
