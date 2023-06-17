package com.just.study.components;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取 yaml 文件配置
 */
@Component("fileComponent")
public class FileComponent {

    @Value("${notes.file.upload.url}")
    public String url;
}
