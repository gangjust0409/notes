package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author abc
 * @since 2023-02-14
 */
@TableName("user_correlation")
@Data
public class UserCorrelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uc_id", type = IdType.AUTO)
    private Long ucId;

    /**
     * 关注谁
     */
    private Long attentionUserId;

    /**
     * 哪个用户关注
     */
    private Long currentUserId;

    private LocalDateTime createdDate;
}
