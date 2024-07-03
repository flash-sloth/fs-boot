package top.fsfsfs.main.logsys.service;

import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.logsys.entity.FsSysLogs;

/**
 * 系统日志 服务层。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */
public interface FsSysLogsService extends SuperService<FsSysLogs> {
    void saveAsync( FsSysLogs fsSysLogs);
}
