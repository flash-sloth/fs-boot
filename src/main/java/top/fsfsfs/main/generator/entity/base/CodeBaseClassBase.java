package top.fsfsfs.main.generator.entity.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.main.generator.enumeration.ClassTypeEnum;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

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
    @Schema(description = "完整包名")
    private String packageName;

    /**
     * 公共字段
     */
    @Schema(description = "公共字段")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<String> fields;
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
