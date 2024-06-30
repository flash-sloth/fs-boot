package top.fsfsfs.demo.test.entity.base;

import java.io.Serializable;
import top.fsfsfs.basic.base.entity.TreeEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 树结构标准字段示例表实体类。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CodeTestTreeBase extends TreeEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_code_test_tree";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 删除标志
     */
    private Long deletedAt;

    /**
     * 删除人
     */
    private Long deletedBy;

    /**
     * 名称
     */
    private String name;

}
