package top.fsfsfs.main.system.dto;

import com.mybatisflex.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 菜单 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-08-01 22:43:23
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜单")
public class SysMenuDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "请填写ID", groups = BaseEntity.Update.class)
    @Schema(description = "ID")
    private Long id;

    /**
     * 子系统ID
     */
    @Schema(description = "子系统ID")
    private Long subSystemId;

    /**
     * 编码;唯一编码，用于区分资源
     */
    @NotEmpty(message = "请填写编码;唯一编码，用于区分资源")
    @Size(max = 255, message = "编码;唯一编码，用于区分资源长度不能超过{max}")
    @Schema(description = "编码;唯一编码，用于区分资源")
    private String code;

    /**
     * 名称
     */
    @NotEmpty(message = "请填写名称")
    @Size(max = 255, message = "名称长度不能超过{max}")
    @Schema(description = "名称")
    private String name;

    /**
     * 类型;[10-目录  20-菜单 30-内链 40-外链]
     */
    @NotEmpty(message = "请填写类型;[10-目录  20-菜单 30-内链 40-外链]")
    @Size(max = 2, message = "类型;[10-目录  20-菜单 30-内链 40-外链]长度不能超过{max}")
    @Schema(description = "类型;[10-目录  20-菜单 30-内链 40-外链]")
    private String menuType;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过{max}")
    @Schema(description = "备注")
    private String remarks;

    /**
     * 地址栏路径
     */
    @Size(max = 255, message = "地址栏路径长度不能超过{max}")
    @Schema(description = "地址栏路径")
    private String path;

    /**
     * 页面地址
     */
    @Size(max = 255, message = "页面地址长度不能超过{max}")
    @Schema(description = "页面地址")
    private String component;

    /**
     * 重定向;用于resource_type=菜单和视图
     */
    @Size(max = 255, message = "重定向;用于resource_type=菜单和视图长度不能超过{max}")
    @Schema(description = "重定向;用于resource_type=菜单和视图")
    private String redirect;

    /**
     * 图标
     */
    @Size(max = 255, message = "图标长度不能超过{max}")
    @Schema(description = "图标")
    private String icon;

    /**
     * 是否公共资源;1-无需分配所有人就可以访问的
     */
    @Schema(description = "是否公共资源;1-无需分配所有人就可以访问的")
    private Boolean isGeneral;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @NotNull(message = "请填写状态;[0-禁用 1-启用]")
    @Schema(description = "状态;[0-禁用 1-启用]")
    private Boolean state;

    /**
     * 布局方式 [01-默认 02-全屏]
     */
    @Size(max = 2, message = "布局方式 [01-默认 02-全屏]长度不能超过{max}")
    @Schema(description = "布局方式 [01-默认 02-全屏]")
    private String layout;

    /**
     * 跳转地址
     */
    @Size(max = 255, message = "跳转地址长度不能超过{max}")
    @Schema(description = "跳转地址")
    private String href;

    /**
     * 是否缓存该路由
     */
    @Schema(description = "是否缓存该路由")
    private Boolean keepAlive;

    /**
     * 路由标题
     */
    @Size(max = 255, message = "路由标题长度不能超过{max}")
    @Schema(description = "路由标题")
    private String title;

    /**
     * 是否在菜单中隐藏该路由
     */
    @Schema(description = "是否在菜单中隐藏该路由")
    private Boolean hideInMenu;

    /**
     * 进入该路由时激活的菜单键
     */
    @Size(max = 255, message = "进入该路由时激活的菜单键长度不能超过{max}")
    @Schema(description = "进入该路由时激活的菜单键")
    private String activeMenu;

    /**
     * 默认情况下，相同路径的路由会共享一个标签页，若设置为true，则使用多个标签页
     */
    @Schema(description = "默认情况下，相同路径的路由会共享一个标签页，若设置为true，则使用多个标签页")
    private Boolean multiTab;

    /**
     * 路由查询参数，如果设置的话，点击菜单进入该路由时会自动携带的query参数
     */
    @Size(max = 255, message = "路由查询参数，如果设置的话，点击菜单进入该路由时会自动携带的query参数长度不能超过{max}")
    @Schema(description = "路由查询参数，如果设置的话，点击菜单进入该路由时会自动携带的query参数")
    private String query;

    /**
     * 树路径
     */
    @Size(max = 512, message = "树路径长度不能超过{max}")
    @Schema(description = "树路径")
    private String treePath;

    /**
     * 树层级
     */
    @Schema(description = "树层级")
    private Integer treeGrade;

    /**
     * 父级ID
     */
    @Schema(description = "父级ID")
    private Long parentId;

    /**
     * 顺序号
     */
    @Schema(description = "顺序号")
    private Integer weight;

    /**
     * 元数据;菜单的元数据
     */
    @Size(max = 512, message = "元数据;菜单的元数据长度不能超过{max}")
    @Schema(description = "元数据;菜单的元数据")
    private String metaJson;

}
