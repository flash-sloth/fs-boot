package top.fsfsfs.boot.modules.generator.query;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.boot.modules.generator.entity.base.CodeCreatorBase;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 代码生成 Query类（查询方法入参）。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "代码生成")
@Table(CodeCreatorBase.TABLE_NAME)
public class CodeCreatorQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Id
    @Schema(description = "编号")
    private Long id;

    /**
     * 表名称
     */
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 表注释
     */
    @Schema(description = "表注释")
    private String tableDescription;

    /**
     * 所属数据源
     */
    @Schema(description = "所属数据源")
    private Long dsId;

    /**
     * 包信息配置
     */
    @Schema(description = "包信息配置")
    private String packageConfig;

    /**
     * 实体类配置
     */
    @Schema(description = "实体类配置")
    private String entityConfig;

    /**
     * VO配置
     */
    @Schema(description = "VO配置")
    private String voConfig;

    /**
     * Xml配置
     */
    @Schema(description = "Xml配置")
    private String xmlConfig;

    /**
     * Mapper配置
     * 
     */
    @Schema(description = "Mapper配置 ")
    private String mapperConfig;

    /**
     * Service配置
     */
    @Schema(description = "Service配置")
    private String serviceConfig;

    /**
     * Controller配置
     */
    @Schema(description = "Controller配置")
    private String controllerConfig;

    /**
     * 菜单配置
     */
    @Schema(description = "菜单配置")
    private String menuConfig;

    /**
     * 主从配置
     */
    @Schema(description = "主从配置")
    private String slaveConfig;

    /**
     * 表格配置
     */
    @Schema(description = "表格配置")
    private String listConfig;

    /**
     * 树结构配置
     */
    @Schema(description = "树结构配置")
    private String treeConfig;

    /**
     * 表单配置
     */
    @Schema(description = "表单配置")
    private String fromConfig;

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
