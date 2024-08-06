package top.fsfsfs.main.system.entity;

import com.mybatisflex.annotation.Table;
import top.fsfsfs.main.system.entity.base.SysButtonBase;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 按钮
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author liyeo
 * @since 2024-08-06 23:02:54
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Schema(description = "按钮")
@Table(SysButtonBase.TABLE_NAME)
public class SysButton extends SysButtonBase {
}
