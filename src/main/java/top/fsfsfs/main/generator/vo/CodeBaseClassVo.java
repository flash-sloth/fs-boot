package top.fsfsfs.main.generator.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.mybatisflex.core.handler.FastjsonTypeHandler;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.main.generator.entity.base.CodeBaseClassBase;

import java.io.Serial;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import top.fsfsfs.main.generator.enumeration.ClassTypeEnum;

/**
 * 基类管理 VO类（通常用作Controller出参）。
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
@Table(value = CodeBaseClassBase.TABLE_NAME)
public class CodeBaseClassVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
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
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<String> fields;
    /**
     * 基类类型; [0-实体  1-Mapper 2-Service 3-Controller]
     */
    @Schema(description = "基类类型; [0-实体  1-Mapper 2-Service 3-Controller]")
    private ClassTypeEnum classType;
    /**
     * 状态; 0-禁用 1-启用
     */
    @Schema(description = "状态; 0-禁用 1-启用")
    private Boolean state;
    /**
     * 顺序;值越小优先级越高
     */
    @Schema(description = "顺序;值越小优先级越高")
    private Integer weight;

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
