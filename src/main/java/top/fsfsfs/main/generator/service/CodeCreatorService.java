package top.fsfsfs.main.generator.service;

import cn.hutool.core.lang.tree.Tree;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.dto.TableImportDto;

import java.util.List;

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

    /**
     * 预览表结构
     * @param ids 表id
     * @return 代码预览树
     */
    List<Tree<Long>> preview(List<Long> ids);
}
