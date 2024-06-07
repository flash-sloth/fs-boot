package top.fsfsfs.boot.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fsfsfs.basic.model.cache.CacheKeyBuilder;
import top.fsfsfs.basic.mvcflex.service.impl.SuperCacheServiceImpl;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple2;
import top.fsfsfs.boot.modules.system.mapper.DefGenTestSimple2Mapper;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimple3Service;

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
public class DefGenTestSimple3ServiceImpl extends SuperCacheServiceImpl<DefGenTestSimple2Mapper, DefGenTestSimple2> implements DefGenTestSimple3Service {

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return () -> "simple3";
    }
}