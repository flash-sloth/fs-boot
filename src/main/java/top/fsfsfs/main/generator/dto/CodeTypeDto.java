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
 * 字段类型管理 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-07-14 11:57:37
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "字段类型管理")
public class CodeTypeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @NotNull(message = "请填写主键ID", groups = BaseEntity.Update.class)
    @Schema(description = "主键ID")
    private Long id;

    /**
     * JDBC字段类型
     */
    @NotEmpty(message = "请填写JDBC字段类型")
    @Size(max = 255, message = "JDBC字段类型长度不能超过{max}")
    @Schema(description = "JDBC字段类型")
    private String jdbcType;

    /**
     * Oracle字段类型
     */
    @Size(max = 255, message = "Oracle字段类型长度不能超过{max}")
    @Schema(description = "Oracle字段类型")
    private String oracleType;

    /**
     * SQLServer字段类型
     */
    @Size(max = 255, message = "SQLServer字段类型长度不能超过{max}")
    @Schema(description = "SQLServer字段类型")
    private String sqlServerType;

    /**
     * Postgre字段类型
     */
    @Size(max = 255, message = "Postgre字段类型长度不能超过{max}")
    @Schema(description = "Postgre字段类型")
    private String postgreType;

    /**
     * 实体类字段完整包名
     */
    @NotEmpty(message = "请填写实体类字段完整包名")
    @Size(max = 255, message = "实体类字段完整包名长度不能超过{max}")
    @Schema(description = "实体类字段完整包名")
    private String javaType;

    /**
     * 实体类字段类型
     */
    @NotEmpty(message = "请填写实体类字段类型")
    @Size(max = 255, message = "实体类字段类型长度不能超过{max}")
    @Schema(description = "实体类字段类型")
    private String javaSimpleType;

    /**
     * 前端字段类型
     */
    @Size(max = 255, message = "前端字段类型长度不能超过{max}")
    @Schema(description = "前端字段类型")
    private String tsType;

}
