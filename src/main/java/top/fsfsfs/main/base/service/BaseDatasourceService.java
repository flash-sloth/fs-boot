package top.fsfsfs.main.base.service;

import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.base.entity.BaseDatasource;

import javax.sql.DataSource;

/**
 * 数据源 服务层。
 *
 * @author tangyh
 * @since 2024-06-30
 */
public interface BaseDatasourceService extends SuperService<BaseDatasource> {
    /** 根据id查询数据源
     * @param dsId 数据源ID
     * @return 数据源
     * */
    DataSource getDs(Long dsId);
}
