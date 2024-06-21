package top.fsfsfs.boot.modules.generator.vo;

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

import java.util.Set;

/**
 * 代码生成 - 导入表 - 入参
 * @author tangyh
 * @since 2022/3/3 14:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "导入表")
public class TableImportDto {

    @NotNull(message = "请选择数据源")
    private Long dsId;
    @NotEmpty(message = "请至少选择一张表")
    private Set<String> tableNames;
}
