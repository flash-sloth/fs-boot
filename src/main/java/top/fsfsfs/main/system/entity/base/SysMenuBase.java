package top.fsfsfs.main.system.entity.base;

import java.io.Serializable;
import top.fsfsfs.basic.base.entity.TreeEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类。
 *
 * @author tangyh
 * @since 2024-08-01 22:43:23
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysMenuBase extends TreeEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_sys_menu";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 子系统ID
     */
    private Long subSystemId;

    /**
     * 编码;唯一编码，用于区分资源
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型;[10-目录  20-菜单 30-内链 40-外链]
     */
    private String menuType;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 地址栏路径
     */
    private String path;

    /**
     * 页面地址
     */
    private String component;

    /**
     * 重定向;用于resource_type=菜单和视图
     */
    private String redirect;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否公共资源;1-无需分配所有人就可以访问的
     */
    private Boolean isGeneral;

    /**
     * 状态;[0-禁用 1-启用]
     */
    private Boolean state;

    /**
     * 布局方式 [01-默认 02-全屏]
     */
    private String layout;

    /**
     * 跳转地址
     */
    private String href;

    /**
     * 是否缓存该路由
     */
    private Boolean keepAlive;

    /**
     * 路由标题
     */
    private String title;

    /**
     * 是否在菜单中隐藏该路由
     */
    private Boolean hideInMenu;

    /**
     * 进入该路由时激活的菜单键
     */
    private String activeMenu;

    /**
     * 默认情况下，相同路径的路由会共享一个标签页，若设置为true，则使用多个标签页
     */
    private Boolean multiTab;

    /**
     * 路由查询参数，如果设置的话，点击菜单进入该路由时会自动携带的query参数
     */
    private String query;

    /**
     * 树路径
     */
    private String treePath;

    /**
     * 树层级
     */
    private Integer treeGrade;

    /**
     * 元数据;菜单的元数据
     */
    private String metaJson;

    /**
     * 删除人
     */
    private Long deletedBy;

    /**
     * 删除标志
     */
    private Long deletedAt;

}
