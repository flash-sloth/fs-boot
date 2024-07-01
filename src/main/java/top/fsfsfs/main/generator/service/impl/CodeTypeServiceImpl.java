package top.fsfsfs.main.generator.service.impl;

import org.springframework.stereotype.Service;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.generator.entity.CodeType;
import top.fsfsfs.main.generator.mapper.CodeTypeMapper;
import top.fsfsfs.main.generator.service.CodeTypeService;

/**
 * 字段类型管理 服务层实现。
 *
 * @author tangyh
 * @since 2024-07-01
 */
@Service
public class CodeTypeServiceImpl extends SuperServiceImpl<CodeTypeMapper, CodeType> implements CodeTypeService {

}
