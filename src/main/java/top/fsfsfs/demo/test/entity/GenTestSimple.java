package top.fsfsfs.demo.test.entity;

import com.mybatisflex.annotation.Table;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.demo.test.entity.base.GenTestSimpleBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 测试单表结构 实体类。
 * DO类：数据对象，可以在关联查询时，再次添加字段，重新生成代码时，忽略此文件。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@Accessors(chain = true)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(value = GenTestSimpleBase.TABLE_NAME, onInsert = DefaultInsertListener.class, onUpdate = DefaultUpdateListener.class)
public class GenTestSimple extends GenTestSimpleBase {
}
