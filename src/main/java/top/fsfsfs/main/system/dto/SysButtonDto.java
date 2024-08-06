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
 * 按钮 DTO（写入方法入参）。
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
public class SysButtonDto implements Serializable {

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
    @NotNull(message = "请填写子系统ID")
    @Schema(description = "子系统ID")
    private Long menuId;

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
     * 图标
     */
    @Size(max = 255, message = "图标长度不能超过{max}")
    @Schema(description = "图标")
    private String icon;

    /**
     * 显示方式 [01-文字 02-图标 03-文字和图标]
     */
    @Size(max = 2, message = "显示方式 [01-文字 02-图标 03-文字和图标]长度不能超过{max}")
    @Schema(description = "显示方式 [01-文字 02-图标 03-文字和图标]")
    private String showMode;

    /**
     * 按钮类型 [01-default 02-primary 03-info 04-success 05-warning 06-error]
     */
    @Size(max = 2, message = "按钮类型 [01-default 02-primary 03-info 04-success 05-warning 06-error]长度不能超过{max}")
    @Schema(description = "按钮类型 [01-default 02-primary 03-info 04-success 05-warning 06-error]")
    private String btnType;

    /**
     * 状态;[0-禁用 1-启用]
     */
    @NotNull(message = "请填写状态;[0-禁用 1-启用]")
    @Schema(description = "状态;[0-禁用 1-启用]")
    private Boolean state;

}
