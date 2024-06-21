package top.fsfsfs.boot.modules.generator.service;

import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.vo.TableImportDto;

/**
 * 代码生成 服务层。
 *
 * @author tangyh
 * @since 2024-06-21
 */
public interface CodeCreatorService extends SuperService<CodeCreator> {

    /**
     * 导入表结构
     * @param importDto 导入参数
     * @return 是否成功
     */
    Boolean importTable(TableImportDto importDto);
}
