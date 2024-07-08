package top.fsfsfs.main.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.QueryWrapperAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import top.fsfsfs.basic.cache.redis.CacheResult;
import top.fsfsfs.basic.cache.repository.CacheOps;
import top.fsfsfs.basic.model.cache.CacheHashKey;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.main.system.dto.SysParamDto;
import top.fsfsfs.main.system.entity.SysParam;
import top.fsfsfs.main.system.mapper.SysParamMapper;
import top.fsfsfs.main.system.service.SysParamService;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Arrays;
import java.util.List;

/**
 * 系统参数 服务层实现。
 *
 * @author hukunzhen
 * @since 2024-07-08 20:18:11
 */
@Slf4j
@Service
public class SysParamServiceImpl extends SuperServiceImpl<SysParamMapper, SysParam> implements SysParamService {
    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    protected CacheOps cacheOps;


    @Override
    public SysParamDto getSysParam( String key) {
        if(key == null){
            return null;
        }
        CacheHashKey cacheHashKey =   new CacheHashKey(StrUtil.join(StrPool.COLON, SysParam.TABLE_NAME, key), key);
        CacheResult<String> objectJson  = cacheOps.get(cacheHashKey, false);
        SysParamDto objRet = null;
        if(objectJson != null && objectJson.getRawValue() != null){
            objRet = JSONUtil.toBean(objectJson.getRawValue(), SysParamDto.class) ;
        }else {
            RefreshSysParamCache();//缓存刷新
            objectJson  = cacheOps.get(cacheHashKey, false);
            if(objectJson.getRawValue() != null) {
                objRet = JSONUtil.toBean(objectJson.getRawValue(), SysParamDto.class);
            }
        }
        return objRet;
    }

    @Override
    public Boolean RefreshSysParamCache(String... keys) {
        List<SysParam> sysParamList = null;
        if(keys.length ==0) {
            sysParamList = this.list();
        }else {
            sysParamList = this.list( QueryWrapper.create()
                    .select()
                    .from( SysParam.TABLE_NAME )
                    .in( SysParam::getKey, Arrays.stream(keys).toList()) );
        }
        sysParamList.stream().forEach(
                x ->{
                    CacheHashKey cKey = new CacheHashKey(StrUtil.join(StrPool.COLON, SysParam.TABLE_NAME, x.getKey()), x.getKey());
                    //CacheHashKey cKey = new CacheHashKey(  SysParam.TABLE_NAME, x.getKey() );
                    cacheOps.set( cKey,
                            JSONUtil.toJsonStr(x) ,
                            false);
                }
            );
        return true;
    }
}
