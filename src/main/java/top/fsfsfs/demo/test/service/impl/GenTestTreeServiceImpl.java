package top.fsfsfs.demo.test.service.impl;

import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.demo.test.entity.GenTestTree;
import top.fsfsfs.demo.test.mapper.GenTestTreeMapper;
import top.fsfsfs.demo.test.service.GenTestTreeService;
import org.springframework.stereotype.Service;

/**
 * 测试树结构 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@Service
public class GenTestTreeServiceImpl extends SuperServiceImpl<GenTestTreeMapper, GenTestTree> implements GenTestTreeService {

}
