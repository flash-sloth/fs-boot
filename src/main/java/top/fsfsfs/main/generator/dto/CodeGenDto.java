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
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.constant.GenerationStrategyEnum;

import java.util.List;
import java.util.Map;

/**
 * 代码生成 - 生成代码 - 入参
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
@Schema(description = "生成代码")
public class CodeGenDto {
    @NotEmpty(message = "请选择想要生成的表")
    private List<Long> ids;
    @NotNull(message = "重新生成")
    private Boolean reload;
    @NotEmpty(message = "请配置生成策略", groups = {BaseEntity.Update.class})
    private Map<GenTypeEnum, GenerationStrategyEnum> genStrategy;
}
