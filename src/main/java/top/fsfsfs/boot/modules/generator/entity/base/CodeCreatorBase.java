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
import top.fsfsfs.boot.modules.generator.entity.type.ControllerDesign;
import top.fsfsfs.boot.modules.generator.entity.type.DtoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.EntityDesign;
import top.fsfsfs.boot.modules.generator.entity.type.MapperDesign;
import top.fsfsfs.boot.modules.generator.entity.type.MenuDesign;
import top.fsfsfs.boot.modules.generator.entity.type.PackageDesign;
import top.fsfsfs.boot.modules.generator.entity.type.QueryDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.boot.modules.generator.entity.type.SlaveDesign;
import top.fsfsfs.boot.modules.generator.entity.type.VoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.XmlDesign;
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
    private PackageDesign packageDesign;

    /**
     * 实体类配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private EntityDesign entityDesign;

    /**
     * VO配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private VoDesign voDesign;

    /**
     * Query配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private QueryDesign queryDesign;

    /**
     * DTO配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private DtoDesign dtoDesign;

    /**
     * Xml配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private XmlDesign xmlDesign;

    /**
     * Mapper配置
     *
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MapperDesign mapperDesign;

    /**
     * Service配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ServiceDesign serviceDesign;
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ServiceImplDesign serviceImplDesign;

    /**
     * Controller配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ControllerDesign controllerDesign;

    /**
     * 菜单配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private MenuDesign menuDesign;

    /**
     * 主从配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private SlaveDesign slaveDesign;

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
