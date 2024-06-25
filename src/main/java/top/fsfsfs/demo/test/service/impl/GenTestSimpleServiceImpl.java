package top.fsfsfs.demo.test.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.demo.test.entity.GenTestSimple;
import top.fsfsfs.demo.test.mapper.GenTestSimpleMapper;
import top.fsfsfs.demo.test.service.GenTestSimpleService;
import org.springframework.stereotype.Service;

/**
 * 测试单表结构 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@Service
public class GenTestSimpleServiceImpl extends SuperServiceImpl<GenTestSimpleMapper, GenTestSimple> implements GenTestSimpleService {

}
