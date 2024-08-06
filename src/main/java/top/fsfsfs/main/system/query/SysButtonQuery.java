package top.fsfsfs.main.system.query;

import com.mybatisflex.annotation.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 按钮 Query类（查询方法入参）。
 *
 * @author liyeo
 * @since 2024-08-06 23:02:54
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "按钮")
public class SysButtonQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

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
     * 创建人id
     */
    @Schema(description = "创建人id")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新人id
     */
    @Schema(description = "更新人id")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

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
