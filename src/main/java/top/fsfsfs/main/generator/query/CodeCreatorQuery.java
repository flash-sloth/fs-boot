package top.fsfsfs.main.generator.query;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.main.generator.entity.base.CodeCreatorBase;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @Schema(description = "Query配置")
    private String queryConfig;
    @Schema(description = "DTO配置")
    private String dtoConfig;

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


    @Schema(description = "ServiceImpl配置")
    private String serviceImplConfig;

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
     * 前端配置
     */
    @Schema(description = "前端配置")
    private String frontDesign;
    @Schema(description = "搜索配置")
    private String searchDesign;

    /**
     * 表格配置
     */
    @Schema(description = "表格配置")
    private String listDesign;
    /**
     * 树结构配置
     */
    @Schema(description = "树结构配置")
    private String treeDesign;

    /**
     * 表单配置
     */
    @Schema(description = "表单配置")
    private String fromDesign;
    @Schema(description = "属性配置")
    private String propertyDesign;
    @Schema(description = "按钮配置")
    private String buttonDesign;

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
