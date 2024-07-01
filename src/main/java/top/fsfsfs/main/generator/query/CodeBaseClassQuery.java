package top.fsfsfs.main.generator.query;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import top.fsfsfs.main.generator.enumeration.ClassTypeEnum;

/**
 * 基类管理 Query类（查询方法入参）。
 *
 * @author tangyh
 * @since 2024-07-01
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "基类管理")
public class CodeBaseClassQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Schema(description = "主键ID")
    private Long id;

    /**
     * 基类名称
     */
    @Schema(description = "基类名称")
    private String name;

    /**
     * 完整包名
     */
    @Schema(description = "完整包名")
    private String packageName;

    /**
     * 公共字段
     */
    @Schema(description = "公共字段")
    private String fields;;
    /**
     * 顺序;值越小优先级越高
     */
    @Schema(description = "顺序;值越小优先级越高")
    private Integer weight;
    /**
     * 状态; 0-禁用 1-启用
     */
    @Schema(description = "状态; 0-禁用 1-启用")
    private Boolean state;
    /**
     * 基类类型; [0-实体  1-Mapper 2-Service 3-Controller]
     */
    @Schema(description = "基类类型; [0-实体  1-Mapper 2-Service 3-Controller]")
    private ClassTypeEnum classType;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

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
