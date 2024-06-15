package top.fsfsfs.boot.modules.system.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * 系统菜单
 *
 * @author tangyh
 * @since 2024-06-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SysMenuVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

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
     * 类型;[10-菜单  20-按钮 30-字段 40-数据]
     */
    private String resourceType;

    /**
     * 打开方式;[01-组件 02-内链 03-外链]

     */
    private String openWith;

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
     * 是否目录
     */
    private Boolean isDir;

    /**
     * 是否隐藏菜单
     */
    private Boolean isHidden;

    /**
     * 是否公共资源;1-无需分配所有人就可以访问的
     */
    private Boolean isGeneral;

    /**
     * 状态;[0-禁用 1-启用]
     */
    private Boolean state;

    /**
     * 分组
     */
    private String subGroup;

    /**
     * 是否脱敏;显示时是否需要脱敏实现 (用于resource_type=字段)
     */
    private Boolean fieldSecret;

    /**
     * 是否禁止编辑;是否可以编辑(用于resource_type=字段)
     */
    private Boolean fieldDisabled;

    /**
     * 数据范围;[01-全部 02-本单位及子级 03-本单位 04-本部门及子级 05-本部门 06-个人 07-自定义]
     */
    private String dataScope;

    /**
     * 实现类;自定义实现类全类名
     */
    private String dataCustomClass;

    /**
     * 是否默认
     */
    private Boolean dataDef;

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
     * 父节点
     */
    private Long parentId;

    /**
     * 顺序号
     */
    private Long weight;

}
