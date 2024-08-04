package top.fsfsfs.main.auth.vo;

import lombok.Data;
import top.fsfsfs.basic.model.Kv;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * Vue路由 Meta
 *
 * @author tangyh
 * @since 2024年06月15日22:18:40
 */
@Data
public class RouterMeta implements Serializable {

    @Serial
    private static final long serialVersionUID = 5499925008927195914L;
    /**
     * 组件
     *
     */
    private String component;

    /**
     * 路由标题
     *
     * 可用于文档标题中
     */
    private String title;
    /**
     * 路由的国际化键值
     *
     * 如果设置，将用于i18n，此时title将被忽略
     */
    private String i18nKey;

    /** 是否缓存该路由 */
    private Boolean keepAlive;
    /**
     * Iconify 图标
     *
     * 可用于菜单或面包屑中
     */
    private String icon;
    /** 路由的外部链接 */
    private String href;
    /** 是否在菜单中隐藏该路由 */
    private Boolean hideInMenu;
    /**
     * 进入该路由时激活的菜单键
     *
     * 该路由不在菜单中
     *
     * @example
     *   假设路由是"user_detail"，如果设置为"user_list"，则会激活"用户列表"菜单项
     */
    private String activeMenu;
    /** 默认情况下，相同路径的路由会共享一个标签页，若设置为true，则使用多个标签页 */

    private Boolean multiTab;
    /** 若设置，路由将在标签页中固定显示，其值表示固定标签页的顺序（首页是特殊的，它将自动保持fixed） */

    private Integer fixedIndexInTab;
    /** 路由查询参数，如果设置的话，点击菜单进入该路由时会自动携带的query参数 */
    private List<Kv> query;
}
