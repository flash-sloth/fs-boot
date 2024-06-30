package top.fsfsfs.demo.test.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.demo.test.entity.CodeTestSimple;
import top.fsfsfs.demo.test.mapper.CodeTestSimpleMapper;
import top.fsfsfs.demo.test.service.CodeTestSimpleService;
import org.springframework.stereotype.Service;

/**
 * 单表标准字段示例表 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Service
public class CodeTestSimpleServiceImpl extends SuperServiceImpl<CodeTestSimpleMapper, CodeTestSimple> implements CodeTestSimpleService {

}
