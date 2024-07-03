package top.fsfsfs.main.logsys.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.logsys.entity.FsSysLogs;
import top.fsfsfs.main.logsys.mapper.FsSysLogsMapper;
import top.fsfsfs.main.logsys.service.FsSysLogsService;
import org.springframework.stereotype.Service;

/**
 * 系统日志 服务层实现。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */
@Slf4j
@Service
public class FsSysLogsServiceImpl extends SuperServiceImpl<FsSysLogsMapper, FsSysLogs> implements FsSysLogsService {

    @Async("mainThreadPoolAsyncExecutor")//指定线程池  top.fsfsfs.config.ThreadPoolAsyncConfig.java
    @Override
    public void saveAsync(FsSysLogs fsSysLogs) {
        log.info("执行【logToDb】任务,线程 : " + Thread.currentThread().getName());
         save(fsSysLogs);
    }
}
