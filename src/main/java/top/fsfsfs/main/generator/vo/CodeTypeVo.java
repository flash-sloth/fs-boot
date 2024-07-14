package top.fsfsfs.main.generator.vo;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.main.generator.entity.base.CodeTypeBase;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 字段类型管理 VO类（通常用作Controller出参）。
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
@Table(value = CodeTypeBase.TABLE_NAME, onInsert = DefaultInsertListener.class, onUpdate = DefaultUpdateListener.class)
public class CodeTypeVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Schema(description = "主键ID")
    private Long id;

    /**
     * JDBC字段类型
     */
    @Schema(description = "JDBC字段类型")
    private String jdbcType;

    /**
     * Oracle字段类型
     */
    @Schema(description = "Oracle字段类型")
    private String oracleType;

    /**
     * SQLServer字段类型
     */
    @Schema(description = "SQLServer字段类型")
    private String sqlServerType;

    /**
     * Postgre字段类型
     */
    @Schema(description = "Postgre字段类型")
    private String postgreType;

    /**
     * 实体类字段完整包名
     */
    @Schema(description = "实体类字段完整包名")
    private String javaType;

    /**
     * 实体类字段类型
     */
    @Schema(description = "实体类字段类型")
    private String javaSimpleType;

    /**
     * 前端字段类型
     */
    @Schema(description = "前端字段类型")
    private String tsType;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updatedAt;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updatedBy;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private Long deletedAt;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private Long deletedBy;

}
