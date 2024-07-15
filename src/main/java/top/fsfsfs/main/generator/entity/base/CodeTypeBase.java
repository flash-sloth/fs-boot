package top.fsfsfs.main.generator.entity.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.CommaSplitTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 字段类型管理实体类。
 *
 * @author tangyh
 * @since 2024-07-15 00:41:12
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
     * 实体类字段完整包名
     */
    private String javaType;

    /**
     * 前端字段类型
     */
    private String tsType;

    /**
     * JDBC字段类型
     */

    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> jdbcType;

    /**
     * Oracle字段类型
     */
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> oracleType;

    /**
     * SQLServer字段类型
     */
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> sqlServerType;

    /**
     * Postgre字段类型
     */
    @Column(typeHandler = CommaSplitTypeHandler.class)
    private List<String> postgreType;

    /**
     * 是否默认类型
     */
    private Boolean def;

    /**
     * 优先级;升序
     */
    private Integer weight;

    /**
     * 删除标志
     */
    private Long deletedAt;

    /**
     * 删除人
     */
    private Long deletedBy;

}
