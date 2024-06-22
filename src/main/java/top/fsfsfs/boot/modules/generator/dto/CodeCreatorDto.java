package top.fsfsfs.boot.modules.generator.dto;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.boot.modules.generator.entity.type.ControllerConfig;
import top.fsfsfs.boot.modules.generator.entity.type.DtoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.EntityConfig;
import top.fsfsfs.boot.modules.generator.entity.type.MapperConfig;
import top.fsfsfs.boot.modules.generator.entity.type.MenuConfig;
import top.fsfsfs.boot.modules.generator.entity.type.PackageConfig;
import top.fsfsfs.boot.modules.generator.entity.type.QueryConfig;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceConfig;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplConfig;
import top.fsfsfs.boot.modules.generator.entity.type.SlaveConfig;
import top.fsfsfs.boot.modules.generator.entity.type.VoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.XmlConfig;
import top.fsfsfs.boot.modules.generator.entity.type.front.ButtonDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.FormDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.ListDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.SearchDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.TreeDesign;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 代码生成 DTO（写入方法入参）。
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
public class CodeCreatorDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotNull(message = "请填写编号", groups = BaseEntity.Update.class)
    @Schema(description = "编号")
    private Long id;

    @Schema(description = "字段配置")
    private List<CodeCreatorColumnDto> columnList;

    /**
     * 表名称
     */
    @NotEmpty(message = "请填写表名称")
    @Size(max = 200, message = "表名称长度不能超过{max}")
    @Schema(description = "表名称")
    private String tableName;

    /**
     * 表注释
     */
    @Size(max = 500, message = "表注释长度不能超过{max}")
    @Schema(description = "表注释")
    private String tableDescription;

    /**
     * 所属数据源
     */
    @NotNull(message = "请填写所属数据源")
    @Schema(description = "所属数据源")
    private Long dsId;

    /**
     * 包信息配置
     */
    @Schema(description = "包信息配置")
    private PackageConfig packageConfig;

    /**
     * 实体类配置
     */
    @Schema(description = "实体类配置")
    private EntityConfig entityConfig;

    /**
     * VO配置
     */
    @Schema(description = "VO配置")
    private VoConfig voConfig;

    /**
     * Query配置
     */
    @Schema(description = "Query配置")
    private QueryConfig queryConfig;

    /**
     * DTO配置
     */
    @Schema(description = "DTO配置")
    private DtoConfig dtoConfig;

    /**
     * Xml配置
     */
    @Schema(description = "Xml配置")
    private XmlConfig xmlConfig;

    /**
     * Mapper配置
     *
     */
    @Schema(description = "Mapper配置 ")
    private MapperConfig mapperConfig;

    /**
     * Service配置
     */
    @Schema(description = "Service配置")
    private ServiceConfig serviceConfig;

    @Schema(description = "ServiceImpl配置")
    private ServiceImplConfig serviceImplConfig;

    /**
     * Controller配置
     */
    @Schema(description = "Controller配置")
    private ControllerConfig controllerConfig;

    /**
     * 菜单配置
     */
    @Schema(description = "菜单配置")
    private MenuConfig menuConfig;

    /**
     * 主从配置
     */
    @Schema(description = "主从配置")
    private SlaveConfig slaveConfig;

    /**
     * 搜索配置
     */
    @Schema(description = "搜索配置")
    private List<SearchDesign> searchDesign;

    /**
     * 表格配置
     */
    @Schema(description = "表格配置")
    private List<ListDesign> listDesign;

    /**
     * 树结构配置
     */
    @Schema(description = "树结构配置")
    private List<ListDesign> treeConfig;

    /**
     * 树结构配置
     */
    @Schema(description = "树结构配置")
    private List<TreeDesign> fromConfig;

    /**
     * 表单配置
     */
    @Schema(description = "表格配置")
    private List<FormDesign> fromDesign;


    /**
     * 属性配置
     */
    @Schema(description = "表格配置")
    private List<PropertyDesign> propertyDesign;

    /**
     * 按钮配置
     */
    @Schema(description = "按钮配置")
    private List<ButtonDesign> buttonDesign;

}
