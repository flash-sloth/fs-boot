package top.fsfsfs.main.base.service.impl;

import cn.hutool.db.ds.DSFactory;
import cn.hutool.setting.Setting;
import org.springframework.stereotype.Service;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.base.entity.BaseDatasource;
import top.fsfsfs.main.base.mapper.BaseDatasourceMapper;
import top.fsfsfs.main.base.service.BaseDatasourceService;
import top.fsfsfs.util.utils.ArgumentAssert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Service
public class BaseDatasourceServiceImpl extends SuperServiceImpl<BaseDatasourceMapper, BaseDatasource> implements BaseDatasourceService {
    private final static Map<String, DataSource> DS_MAP = new HashMap<>();

    @Override
    public DataSource getDs(Long dsId) {
        ArgumentAssert.notNull(dsId, "请选择数据源:{}", dsId);
        BaseDatasource baseDatasource = getById(dsId);
        ArgumentAssert.notNull(baseDatasource, "请先配置数据源:{}", dsId);

        String key = baseDatasource.getUrl() + baseDatasource.getDriverClass() + baseDatasource.getUsername() + baseDatasource.getPassword();
        if (DS_MAP.containsKey(key)) {
            return DS_MAP.get(key);
        }

        String group = baseDatasource.getName();
        Setting setting = Setting.create()
                .setByGroup("url", group, baseDatasource.getUrl())
                .setByGroup("username", group, baseDatasource.getUsername())
                .setByGroup("password", group, baseDatasource.getPassword())
                .setByGroup("driver", group, baseDatasource.getDriverClass())
                .setByGroup("initialSize", group, "1")
                .setByGroup("maxActive", group, "1")
                .setByGroup("minIdle", group, "1")
//                .setByGroup("validationQuery", group, validationQuery)
                .setByGroup("connectionErrorRetryAttempts", group, "0")
                .setByGroup("breakAfterAcquireFailure", group, "true")
                // 5.7 版本支持注释
                .setByGroup("useInformationSchema", group, "true")
                .setByGroup("remarks", group, "true");
        DSFactory dsFactory = DSFactory.create(setting);
        DataSource ds = dsFactory.getDataSource(group);
        DS_MAP.put(key, ds);
        return ds;
    }
}
