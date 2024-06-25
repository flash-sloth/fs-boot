package top.fsfsfs.main.generator.vo.column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.fsfsfs.main.generator.entity.type.front.FormDesign;
import top.fsfsfs.main.generator.entity.type.front.ListDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.entity.type.front.TreeDesign;

/**
 * 代码生成器 实体类字段配置
 *
 * @author tangyh
 * @since 2021-08-01 16:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDesignVO {

    private Long codeCreatorId;
    private String column;
    private String columnType;
    private String columnDescription;
    private Integer length;
    private Integer digit;

    private ListDesign listConfig;
    private TreeDesign treeConfig;
    private FormDesign formConfig;
    private PropertyDesign propertyConfig;
}
