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
public class Catalog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "catalog_id", type = IdType.AUTO)
    private Long catalogId;

    /**
     * 分类名称
     */
    private String catalogName;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 0 未删除 1 已删除
     */
    private Integer status;

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Catalog{" +
            "catalogId=" + catalogId +
            ", catalogName=" + catalogName +
            ", parentId=" + parentId +
            ", status=" + status +
        "}";
    }
}
