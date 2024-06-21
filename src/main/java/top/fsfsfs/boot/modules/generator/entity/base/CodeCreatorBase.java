package top.fsfsfs.boot.modules.generator.entity.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.boot.modules.generator.entity.type.ControllerConfig;
import top.fsfsfs.boot.modules.generator.entity.type.EntityConfig;
import top.fsfsfs.boot.modules.generator.entity.type.MapperConfig;
import top.fsfsfs.boot.modules.generator.entity.type.QueryConfig;
import top.fsfsfs.boot.modules.generator.entity.type.DtoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.MenuConfig;
import top.fsfsfs.boot.modules.generator.entity.type.PackageConfig;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceConfig;
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
 * 代码生成 实体类。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CodeCreatorBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_creator";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableDescription;

    /**
     * 所属数据源
     */
    private Long dsId;

    /**
     * 包信息配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private PackageConfig packageConfig;

    /**
     * 实体类配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private EntityConfig entityConfig;

    /**
     * VO配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private VoConfig voConfig;

    /**
     * Query配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private QueryConfig queryConfig;

    /**
     * DTO配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private DtoConfig dtoConfig;

    /**
     * Xml配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private XmlConfig xmlConfig;

    /**
     * Mapper配置
     *
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MapperConfig mapperConfig;

    /**
     * Service配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ServiceConfig serviceConfig;

    /**
     * Controller配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ControllerConfig controllerConfig;

    /**
     * 菜单配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MenuConfig menuConfig;

    /**
     * 主从配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private SlaveConfig slaveConfig;

    /**
     * 搜索配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<SearchDesign> searchDesign;

    /**
     * 表格配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<ListDesign> listDesign;
    /**
     * 树结构配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<TreeDesign> treeDesign;
    /**
     * 表单配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<FormDesign> fromDesign;

    /**
     * 属性配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<PropertyDesign> propertyDesign;
    /**
     * 按钮配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private List<ButtonDesign> buttonDesign;

    /**
     * 删除者
     */
    private Long deletedBy;

    /**
     * 删除标志
     */
    private Long deletedAt;

}
