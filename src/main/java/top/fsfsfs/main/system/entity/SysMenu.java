package top.fsfsfs.main.system.entity;

import com.mybatisflex.annotation.Table;
import top.fsfsfs.main.system.entity.base.SysMenuBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 菜单实体类。
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author tangyh
 * @since 2024-07-23 19:20:21
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(SysMenuBase.TABLE_NAME)
public class SysMenu extends SysMenuBase {
}
