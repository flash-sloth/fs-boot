package top.fsfsfs.boot.modules.generator.vo;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.boot.modules.generator.entity.base.CodeCreatorColumnBase;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 代码生成字段 VO类（通常用作Controller出参）。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "代码生成字段")
@Table(value = CodeCreatorColumnBase.TABLE_NAME, onInsert = DefaultInsertListener.class, onUpdate = DefaultUpdateListener.class)
public class CodeCreatorColumnVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Schema(description = "ID")
    private Long id;

    /**
     * 所属表ID
     */
    @Schema(description = "所属表ID")
    private Long codeCreatorId;

    /**
     * 数据库列名称
     */
    @Schema(description = "数据库列名称")
    private String name;

    /**
     * 列类型
     */
    @Schema(description = "列类型")
    private String typeName;

    /**
     * 注释
     */
    @Schema(description = "注释")
    private String remarks;

    /**
     * 长度
     */
    @Schema(description = "长度")
    private Integer size;

    /**
     * 小数位数
     */
    @Schema(description = "小数位数")
    private Integer digit;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private Boolean isPk;

    /**
     * 自增
     */
    @Schema(description = "自增")
    private Boolean autoIncrement;

    /**
     * 是否为可空
     */
    @Schema(description = "是否为可空")
    private Boolean isNullable;

    /**
     * 默认值
     */
    @Schema(description = "默认值")
    private String defValue;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer weight;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    private Long createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    private Long updatedBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

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
