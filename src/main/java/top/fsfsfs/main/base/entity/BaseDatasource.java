package top.fsfsfs.main.base.entity;

import com.mybatisflex.annotation.Table;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import top.fsfsfs.main.base.entity.base.BaseDatasourceBase;

/**
 * 数据源
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(description = "数据源")
@Table(value = BaseDatasourceBase.TABLE_NAME)
public class BaseDatasource extends BaseDatasourceBase {
}
