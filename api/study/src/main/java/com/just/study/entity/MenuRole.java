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
 * @since 2023-02-24
 */
@TableName("menu_role")
public class MenuRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "menu_role_id", type = IdType.AUTO)
    private Long menuRoleId;

    /**
     * 菜单 id
     */
    private Long menuId;

    /**
     * 角色id
     */
    private Long roleId;

    public Long getMenuRoleId() {
        return menuRoleId;
    }

    public void setMenuRoleId(Long menuRoleId) {
        this.menuRoleId = menuRoleId;
    }
    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "MenuRole{" +
            "menuRoleId=" + menuRoleId +
            ", menuId=" + menuId +
            ", roleId=" + roleId +
        "}";
    }
}
