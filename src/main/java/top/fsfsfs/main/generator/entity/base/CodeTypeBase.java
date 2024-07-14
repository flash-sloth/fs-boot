package top.fsfsfs.main.generator.entity.base;

import java.io.Serializable;
import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 字段类型管理实体类。
 *
 * @author tangyh
 * @since 2024-07-14 11:57:37
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CodeTypeBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_type";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * JDBC字段类型
     */
    private String jdbcType;

    /**
     * Oracle字段类型
     */
    private String oracleType;

    /**
     * SQLServer字段类型
     */
    private String sqlServerType;

    /**
     * Postgre字段类型
     */
    private String postgreType;

    /**
     * 实体类字段完整包名
     */
    private String javaType;

    /**
     * 实体类字段类型
     */
    private String javaSimpleType;

    /**
     * 前端字段类型
     */
    private String tsType;

    /**
     * 删除标志
     */
    private Long deletedAt;

    /**
     * 删除人
     */
    private Long deletedBy;

}
