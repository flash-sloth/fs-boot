package top.fsfsfs.main.generator.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.generator.entity.CodeType;
import top.fsfsfs.main.generator.mapper.CodeTypeMapper;
import top.fsfsfs.main.generator.service.CodeTypeService;

import java.util.List;

/**
 * 字段类型管理 服务层实现。
 *
 * @author tangyh
 * @since 2024-07-15 00:41:12
 */
@Service
public class CodeTypeServiceImpl extends SuperServiceImpl<CodeTypeMapper, CodeType> implements CodeTypeService {
    @Override
    @Transactional(readOnly = true)
    public List<CodeType> listAll() {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderBy(CodeType::getWeight, false);
        return list(wrapper);
    }
}
