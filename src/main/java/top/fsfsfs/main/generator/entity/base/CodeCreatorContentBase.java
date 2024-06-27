package top.fsfsfs.main.generator.entity.base;

import java.io.Serializable;

import com.mybatisflex.codegen.constant.GenTypeEnum;
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
 * 代码生成内容 实体类。
 *
 * @author tangyh
 * @since 2024-06-27
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "代码生成内容")
public class CodeCreatorContentBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_creator_content";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表ID
     */
    @Schema(description = "表ID")
    private Long codeCreatorId;

    /**
     * 代码类型
     */
    @Schema(description = "代码类型")
    private GenTypeEnum genType;

    /**
     * 包信息配置
     */
    @Schema(description = "包信息配置")
    private String content;

    /**
     * 删除者
     */
    @Schema(description = "删除者")
    private Long deletedBy;

    /**
     * 删除标志
     */
    @Schema(description = "删除标志")
    private Long deletedAt;

}
