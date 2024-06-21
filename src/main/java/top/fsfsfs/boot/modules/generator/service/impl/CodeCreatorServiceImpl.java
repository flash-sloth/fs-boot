package top.fsfsfs.boot.modules.generator.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorService;
import org.springframework.stereotype.Service;

/**
 * 代码生成 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Service
public class CodeCreatorServiceImpl extends SuperServiceImpl<CodeCreatorMapper, CodeCreator> implements CodeCreatorService {

}
