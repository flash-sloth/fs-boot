package top.fsfsfs.main.generator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 代码生成 - 预览代码 - 入参
 * @author tangyh
 * @since 2024/6/25 22:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "预览代码")
public class CodePreviewDto {
    @NotEmpty(message = "请选择想要生成的表")
    private List<Long> ids;
    @NotNull(message = "请填写是否需要重新生成")
    private Boolean reload;
}
