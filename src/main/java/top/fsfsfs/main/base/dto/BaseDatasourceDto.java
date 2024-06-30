package top.fsfsfs.main.base.dto;

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
 * 数据源 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "数据源")
public class BaseDatasourceDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull(message = "请填写主键ID", groups = BaseEntity.Update.class)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 名称
     */
    @NotEmpty(message = "请填写名称")
    @Size(max = 255, message = "名称长度不能超过{max}")
    @Schema(description = "名称")
    private String name;

    /**
     * 账号
     */
    @NotEmpty(message = "请填写账号")
    @Size(max = 255, message = "账号长度不能超过{max}")
    @Schema(description = "账号")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "请填写密码")
    @Size(max = 255, message = "密码长度不能超过{max}")
    @Schema(description = "密码")
    private String password;

    /**
     * 链接
     */
    @NotEmpty(message = "请填写链接")
    @Size(max = 255, message = "链接长度不能超过{max}")
    @Schema(description = "链接")
    private String url;

    /**
     * 驱动
     */
    @NotEmpty(message = "请填写驱动")
    @Size(max = 255, message = "驱动长度不能超过{max}")
    @Schema(description = "驱动")
    private String driverClass;

}
