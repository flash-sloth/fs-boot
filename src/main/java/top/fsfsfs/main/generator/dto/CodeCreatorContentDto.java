package top.fsfsfs.main.generator.dto;

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
 * 代码生成内容 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-06-27
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "代码生成内容")
public class CodeCreatorContentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(message = "请填写编号", groups = BaseEntity.Update.class)
    @Schema(description = "编号")
    private Long id;

    /**
     * 表ID
     */
    @NotNull(message = "请填写表ID")
    @Schema(description = "表ID")
    private Long codeCreatorId;

    /**
     * 代码类型
     */
    @NotEmpty(message = "请填写代码类型")
    @Size(max = 255, message = "代码类型长度不能超过{max}")
    @Schema(description = "代码类型")
    private String genType;

    /**
     * 包信息配置
     */
    @Size(max = 536870911, message = "包信息配置长度不能超过{max}")
    @Schema(description = "包信息配置")
    private String content;

}
