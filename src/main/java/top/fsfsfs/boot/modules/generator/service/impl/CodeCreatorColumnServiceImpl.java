package top.fsfsfs.boot.modules.generator.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.modules.generator.entity.CodeCreatorColumn;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorColumnService;
import org.springframework.stereotype.Service;

/**
 * 代码生成字段 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Service
public class CodeCreatorColumnServiceImpl extends SuperServiceImpl<CodeCreatorColumnMapper, CodeCreatorColumn> implements CodeCreatorColumnService {

}
