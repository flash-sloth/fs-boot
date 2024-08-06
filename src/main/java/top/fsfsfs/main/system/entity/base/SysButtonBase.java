package top.fsfsfs.main.system.entity.base;

import java.io.Serializable;
import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 按钮
 *
 * @author liyeo
 * @since 2024-08-06 23:02:54
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "按钮")
public class SysButtonBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_sys_button";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 子系统ID
     */
    @Schema(description = "子系统ID")
    private Long menuId;

    /**
     * 编码;唯一编码，用于区分资源
     */
    @Schema(description = "编码;唯一编码，用于区分资源")
    private String code;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 显示方式 [01-文字 02-图标 03-文字和图标]
     */
    @Schema(description = "显示方式 [01-文字 02-图标 03-文字和图标]")
    private String showMode;

    /**
     * 按钮类型 [01-default 02-primary 03-info 04-success 05-warning 06-error]
     */
    @Schema(description = "按钮类型 [01-default 02-primary 03-info 04-success 05-warning 06-error]")
    private String btnType;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @Schema(description = "状态;[0-禁用 1-启用]")
    private Boolean state;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private Long deletedBy;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private Long deletedAt;

}
