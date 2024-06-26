package top.fsfsfs.main.generator.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.main.generator.entity.base.CodeCreatorBase;
import top.fsfsfs.main.generator.entity.type.PackageDesign;

import top.fsfsfs.main.generator.entity.type.ControllerDesign;
import top.fsfsfs.main.generator.entity.type.EntityDesign;
import top.fsfsfs.main.generator.entity.type.MapperDesign;
import top.fsfsfs.main.generator.entity.type.QueryDesign;
import top.fsfsfs.main.generator.entity.type.DtoDesign;
import top.fsfsfs.main.generator.entity.type.MenuDesign;
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
import java.time.LocalDateTime;
import java.util.List;

/**
 * 代码生成 VO类（通常用作Controller出参）。
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
public class CodeCreatorVo implements Serializable {

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
    @Column(typeHandler = FastjsonTypeHandler.class)
    private PackageDesign packageDesign;

    /**
     * 实体类配置
     */
    @Schema(description = "实体类配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private EntityDesign entityDesign;

    /**
     * VO配置
     */
    @Schema(description = "VO配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private VoDesign voDesign;

    /**
     * Query配置
     */
    @Schema(description = "Query配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private QueryDesign queryDesign;

    /**
     * DTO配置
     */
    @Schema(description = "DTO配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private DtoDesign dtoDesign;

    /**
     * Xml配置
     */
    @Schema(description = "Xml配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private XmlDesign xmlDesign;

    /**
     * Mapper配置
     *
     */
    @Schema(description = "Mapper配置 ")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MapperDesign mapperDesign;

    /**
     * Service配置
     */
    @Schema(description = "Service配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ServiceDesign serviceDesign;

    @Schema(description = "ServiceImpl配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ServiceImplDesign serviceImplDesign;
    /**
     * Controller配置
     */
    @Schema(description = "Controller配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ControllerDesign controllerDesign;

    /**
     * 菜单配置
     */
    @Schema(description = "菜单配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MenuDesign menuDesign;

    /**
     * 主从配置
     */
    @Schema(description = "主从配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private SlaveDesign slaveDesign;
    /**
     * 前端配置
     */
    @Schema(description = "前端配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private FrontDesign frontDesign;
    /**
     * 搜索配置
     */
    @Schema(description = "搜索配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<SearchDesign> searchDesign;

    /**
     * 表格配置
     */
    @Schema(description = "表格配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<ListDesign> listDesign;
    /**
     * 树结构配置
     */
    @Schema(description = "树结构配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<TreeDesign> treeDesign;
    /**
     * 表单配置
     */
    @Schema(description = "表单配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<FormDesign> fromDesign;

    /**
     * 属性配置
     */
    @Schema(description = "属性配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<PropertyDesign> propertyDesign;
    /**
     * 按钮配置
     */
    @Schema(description = "按钮配置")
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<ButtonDesign> buttonDesign;

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
