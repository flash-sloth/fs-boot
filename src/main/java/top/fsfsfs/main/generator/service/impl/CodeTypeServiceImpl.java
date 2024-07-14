package top.fsfsfs.main.generator.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.generator.entity.CodeType;
import top.fsfsfs.main.generator.mapper.CodeTypeMapper;
import top.fsfsfs.main.generator.service.CodeTypeService;
import org.springframework.stereotype.Service;

/**
 * 字段类型管理 服务层实现。
 *
 * @author tangyh
 * @since 2024-07-14 11:57:37
 */
@Service
public class CodeTypeServiceImpl extends SuperServiceImpl<CodeTypeMapper, CodeType> implements CodeTypeService {

}
