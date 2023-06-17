package com.just.study.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author abc
 * @since 2023-02-24
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 父级 id
     */
    private Long parentId;

    /**
     * 层级
     */
    private Long level;

    /**
     * 组件
     */
    private String component;

    /**
     * 是否启用
     */
    private Integer enable;

    /**
     * 排序
     */
    private Long orderSort;

    /**
     * 状态
     */
    private Long status;

    private String path;

    @TableField(exist = false)
    private List<Menu> children;

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }
    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }
    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }
    public Long getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(Long orderSort) {
        this.orderSort = orderSort;
    }
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", parentId=" + parentId +
                ", level=" + level +
                ", component='" + component + '\'' +
                ", enable=" + enable +
                ", orderSort=" + orderSort +
                ", status=" + status +
                ", path='" + path + '\'' +
                ", children=" + children +
                '}';
    }
}
