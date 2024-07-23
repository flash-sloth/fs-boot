package top.fsfsfs.main.generator.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.main.generator.entity.base.CodeCreatorColumnBase;

/**
 * 代码生成字段 实体类。
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(value = CodeCreatorColumnBase.TABLE_NAME)
public class CodeCreatorColumn extends CodeCreatorColumnBase {
}
