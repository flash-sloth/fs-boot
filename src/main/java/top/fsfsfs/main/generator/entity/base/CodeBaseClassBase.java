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
 * 基类管理
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
@Schema(description = "基类管理")
public class CodeBaseClassBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_base_class";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 基类名称
     */
    @Schema(description = "基类名称")
    private String name;

    /**
     * 完整包名
     */
    @Column("packageName")
    @Schema(description = "完整包名")
    private String packageName;

    /**
     * 公共字段
     */
    @Schema(description = "公共字段")
    private String fields;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

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
