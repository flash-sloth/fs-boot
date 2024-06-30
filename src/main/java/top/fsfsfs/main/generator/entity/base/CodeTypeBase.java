package top.fsfsfs.main.generator.entity.base;

import com.mybatisflex.annotation.Column;
import java.io.Serializable;
import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 字段类型管理 
 *
 * @author tangyh
 * @since 2024-07-01
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "字段类型管理")
public class CodeTypeBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_type";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 数据库字段类型
     */
    @Column("jdbcType")
    @Schema(description = "数据库字段类型")
    private String jdbcType;

    /**
     * 实体类字段类型
     */
    @Column("javaType")
    @Schema(description = "实体类字段类型")
    private String javaType;

    /**
     * 实体类字段完整包名
     */
    @Column("javaPackage")
    @Schema(description = "实体类字段完整包名")
    private String javaPackage;

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
