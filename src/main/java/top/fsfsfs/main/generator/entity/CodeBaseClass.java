package top.fsfsfs.main.generator.entity;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.main.generator.entity.base.CodeBaseClassBase;

/**
 * 基类管理
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author tangyh
 * @since 2024-07-01
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(description = "基类管理")
@Table(value = CodeBaseClassBase.TABLE_NAME)
public class CodeBaseClass extends CodeBaseClassBase {
}
