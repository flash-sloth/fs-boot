package top.fsfsfs.boot.modules.generator.dto;

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
 * 代码生成字段 DTO（写入方法入参）。
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
public class CodeCreatorColumnDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "请填写ID", groups = BaseEntity.Update.class)
    @Schema(description = "ID")
    private Long id;

    /**
     * 所属表ID
     */
    @NotNull(message = "请填写所属表ID")
    @Schema(description = "所属表ID")
    private Long codeCreatorId;

    /**
     * 数据库列名称
     */
    @NotEmpty(message = "请填写数据库列名称")
    @Size(max = 255, message = "数据库列名称长度不能超过{max}")
    @Schema(description = "数据库列名称")
    private String name;

    /**
     * 列类型
     */
    @NotEmpty(message = "请填写列类型")
    @Size(max = 255, message = "列类型长度不能超过{max}")
    @Schema(description = "列类型")
    private String typeName;

    /**
     * 注释
     */
    @Size(max = 512, message = "注释长度不能超过{max}")
    @Schema(description = "注释")
    private String remarks;

    /**
     * 长度
     */
    @NotNull(message = "请填写长度")
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
    @NotNull(message = "请填写主键")
    @Schema(description = "主键")
    private Boolean isPk;

    /**
     * 自增
     */
    @NotNull(message = "请填写自增")
    @Schema(description = "自增")
    private Boolean autoIncrement;

    /**
     * 是否为可空
     */
    @NotNull(message = "请填写是否为可空")
    @Schema(description = "是否为可空")
    private Boolean isNullable;

    /**
     * 默认值
     */
    @Size(max = 512, message = "默认值长度不能超过{max}")
    @Schema(description = "默认值")
    private String defValue;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer weight;

}
