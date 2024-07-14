package top.fsfsfs.main.generator.entity.base;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.handler.FastjsonTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.main.generator.entity.type.front.FormDesign;
import top.fsfsfs.main.generator.entity.type.front.ListDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.entity.type.front.SearchDesign;
import top.fsfsfs.main.generator.entity.type.front.TreeDesign;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代码生成字段 实体类。
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
public class CodeCreatorColumnBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_creator_column";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 所属表ID
     */
    private Long codeCreatorId;

    /**
     * 数据库列名称
     */
    private String name;

    /**
     * 列类型
     */
    private String typeName;

    /**
     * 注释
     */
    private String remarks;

    /**
     * 长度
     */
    private Integer size;

    /**
     * 小数位数
     */
    private Integer digit;

    /**
     * 主键
     */
    private Boolean isPk;

    /**
     * 自增
     */
    private Boolean autoIncrement;

    /**
     * 是否为可空
     */
    private Boolean isNullable;

    /**
     * 默认值
     */
    private String defValue;

    /**
     * 排序
     */
    private Integer weight;


    /**
     * 搜索配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private SearchDesign searchDesign;

    /**
     * 表格配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private ListDesign listDesign;
    /**
     * 表单配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private FormDesign formDesign;

    /**
     * 属性配置
     */
    @Column(typeHandler = FastjsonTypeHandler.class)
    private PropertyDesign propertyDesign;

    /**
     * 删除者
     */
    private Long deletedBy;

    /**
     * 删除标志
     */
    private Long deletedAt;

}
