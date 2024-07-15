package top.fsfsfs.main.generator.service;

import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.generator.entity.CodeType;

import java.util.List;

/**
 * 字段类型管理 服务层。
 *
 * @author tangyh
 * @since 2024-07-15 00:41:12
 */
public interface CodeTypeService extends SuperService<CodeType> {
    /**
     * 查询所有
     * @return 所有字段类型
     */
    List<CodeType> listAll();
}
