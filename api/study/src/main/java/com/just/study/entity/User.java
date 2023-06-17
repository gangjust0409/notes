package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-13
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long userId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 关注
     */
    private Long follow;

    /**
     * 粉丝
     */
    private Long fans;

    /**
     * 头像
     */
    private String pic;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    /**
     * 是否关注过该用户
     */
    @TableField(exist = false)
    private Boolean attention;

}
