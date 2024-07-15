package top.fsfsfs.main.generator.dto;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.core.handler.CommaSplitTypeHandler;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;
import java.util.List;

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
 * @since 2024-07-15 00:41:12
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
     * 实体类字段完整包名
     */
    @NotEmpty(message = "请填写实体类字段完整包名")
    @Size(max = 255, message = "实体类字段完整包名长度不能超过{max}")
    @Schema(description = "实体类字段完整包名")
    private String javaType;

    /**
     * 前端字段类型
     */
    @Size(max = 255, message = "前端字段类型长度不能超过{max}")
    @Schema(description = "前端字段类型")
    private String tsType;

    /**
     * JDBC字段类型
     */
    @NotNull(message = "请填写JDBC字段类型")
    @Column(typeHandler = CommaSplitTypeHandler.class)
    @Schema(description = "JDBC字段类型")
    private List<String> jdbcType;

    /**
     * Oracle字段类型
     */
    @Size(max = 255, message = "Oracle字段类型长度不能超过{max}")
    @Schema(description = "Oracle字段类型")
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> oracleType;

    /**
     * SQLServer字段类型
     */
    @Size(max = 255, message = "SQLServer字段类型长度不能超过{max}")
    @Schema(description = "SQLServer字段类型")
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> sqlServerType;

    /**
     * Postgre字段类型
     */
    @Size(max = 255, message = "Postgre字段类型长度不能超过{max}")
    @Schema(description = "Postgre字段类型")
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> postgreType;

    /**
     * 是否默认类型
     */
    @Schema(description = "是否默认类型")
    private Boolean def;

    /**
     * 优先级;升序
     */
    @Schema(description = "优先级;升序")
    private Integer weight;

}
