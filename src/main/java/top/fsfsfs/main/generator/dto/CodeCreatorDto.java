package top.fsfsfs.main.generator.dto;

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
import top.fsfsfs.main.generator.entity.type.ControllerDesign;
import top.fsfsfs.main.generator.entity.type.DtoDesign;
import top.fsfsfs.main.generator.entity.type.EntityDesign;
import top.fsfsfs.main.generator.entity.type.MapperDesign;
import top.fsfsfs.main.generator.entity.type.MenuDesign;
import top.fsfsfs.main.generator.entity.type.PackageDesign;
import top.fsfsfs.main.generator.entity.type.QueryDesign;
import top.fsfsfs.main.generator.entity.type.ServiceDesign;
import top.fsfsfs.main.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.main.generator.entity.type.SlaveDesign;
import top.fsfsfs.main.generator.entity.type.VoDesign;
import top.fsfsfs.main.generator.entity.type.XmlDesign;
import top.fsfsfs.main.generator.entity.type.front.ButtonDesign;
import top.fsfsfs.main.generator.entity.type.front.FormDesign;
import top.fsfsfs.main.generator.entity.type.front.FrontDesign;
import top.fsfsfs.main.generator.entity.type.front.ListDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.entity.type.front.SearchDesign;
import top.fsfsfs.main.generator.entity.type.front.TreeDesign;

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
    private PackageDesign packageDesign;

    /**
     * 实体类配置
     */
    @Schema(description = "实体类配置")
    private EntityDesign entityDesign;

    /**
     * VO配置
     */
    @Schema(description = "VO配置")
    private VoDesign voDesign;

    /**
     * Query配置
     */
    @Schema(description = "Query配置")
    private QueryDesign queryDesign;

    /**
     * DTO配置
     */
    @Schema(description = "DTO配置")
    private DtoDesign dtoDesign;

    /**
     * Xml配置
     */
    @Schema(description = "Xml配置")
    private XmlDesign xmlDesign;

    /**
     * Mapper配置
     *
     */
    @Schema(description = "Mapper配置 ")
    private MapperDesign mapperDesign;

    /**
     * Service配置
     */
    @Schema(description = "Service配置")
    private ServiceDesign serviceDesign;

    @Schema(description = "ServiceImpl配置")
    private ServiceImplDesign serviceImplDesign;

    /**
     * Controller配置
     */
    @Schema(description = "Controller配置")
    private ControllerDesign controllerDesign;

    /**
     * 菜单配置
     */
    @Schema(description = "菜单配置")
    private MenuDesign menuDesign;

    /**
     * 主从配置
     */
    @Schema(description = "主从配置")
    private SlaveDesign slaveDesign;
    /**
     * 前端配置
     */
    @Schema(description = "前端配置")
    private FrontDesign frontDesign;
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
    private List<TreeDesign> treeDesign;

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
