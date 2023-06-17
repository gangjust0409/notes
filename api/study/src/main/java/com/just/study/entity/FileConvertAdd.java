package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author abc
 * @since 2023-02-09
 */
@TableName("file_convert_add")
@Data
public class FileConvertAdd implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 上传文件中间转换名字到添加
     */
    private String fileName;

    /**
     * 唯一标识
     */
    private String fileUnique;
}
