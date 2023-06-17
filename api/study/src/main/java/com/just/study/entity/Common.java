package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@Data
public class Common implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "common_id", type = IdType.AUTO)
    private Long commonId;

    /**
     * 文章 id
     */
    private Long articleId;

    /**
     * 当前评论的用户
     */
    private Long userId;

    /**
     * 评论的内容
     */
    private String content;

    /**
     * 评论的日期
     */
    private LocalDateTime commonDate;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    /**
     * 回复谁
     */
    private String reply;

    @TableField(exist = false)
    private List<Common> children;

    @TableField(exist = false)
    private User user;

    /**
     * 用于显示评论是否是作者
     */
    @TableField(exist = false)
    private boolean author;

    /**
     * 该评论是否是当前用户登录评论的
     */
    @TableField(exist = false)
    private boolean delete;
}
