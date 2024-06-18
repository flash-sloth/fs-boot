package top.fsfsfs.boot.modules.test.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fsfsfs.basic.model.cache.CacheKeyBuilder;
import top.fsfsfs.boot.modules.test.entity.DefGenTestTree;
import top.fsfsfs.boot.modules.test.mapper.DefGenTestTreeMapper;
import top.fsfsfs.boot.modules.test.service.DefGenTestTreeService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
/**
 * <p>
 * 系统授权表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-01-22
 */
@Service
@RequiredArgsConstructor
public class DefGenTestTreeServiceImpl extends SuperServiceImpl<DefGenTestTreeMapper, DefGenTestTree> implements DefGenTestTreeService {

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return null;
//        return () -> "simple3";
    }
}