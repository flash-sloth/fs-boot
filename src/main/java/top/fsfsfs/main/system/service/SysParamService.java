package top.fsfsfs.main.system.service;

import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.system.dto.SysParamDto;
import top.fsfsfs.main.system.entity.SysParam;

/**
 * 系统参数 服务层。
 *
 * @author hukunzhen
 * @since 2024-07-08 20:18:11
 */
public interface SysParamService extends SuperService<SysParam> {

    SysParamDto getSysParam(String key);

    /**
     * 刷新缓存
     * @param key
     * @return
     */
    Boolean RefreshSysParamCache(String ... key);
}
