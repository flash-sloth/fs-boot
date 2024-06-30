package top.fsfsfs.main.generator.dto;

import com.mybatisflex.annotation.Column;
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
 * 基类管理 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-07-01
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "基类管理")
public class CodeBaseClassDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull(message = "请填写主键ID", groups = BaseEntity.Update.class)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 基类名称
     */
    @NotEmpty(message = "请填写基类名称")
    @Size(max = 255, message = "基类名称长度不能超过{max}")
    @Schema(description = "基类名称")
    private String name;

    /**
     * 完整包名
     */
    @NotEmpty(message = "请填写完整包名")
    @Size(max = 255, message = "完整包名长度不能超过{max}")
    @Schema(description = "完整包名")
    private String packageName;

    /**
     * 公共字段
     */
    @Size(max = 255, message = "公共字段长度不能超过{max}")
    @Schema(description = "公共字段")
    private String fields;

    /**
     * 备注
     */
    @Size(max = 255, message = "备注长度不能超过{max}")
    @Schema(description = "备注")
    private String remark;

}
