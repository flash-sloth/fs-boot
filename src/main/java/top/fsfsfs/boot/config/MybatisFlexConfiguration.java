package top.fsfsfs.boot.config;

import com.mybatisflex.core.audit.AuditManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import top.fsfsfs.basic.db.properties.DatabaseProperties;
import top.fsfsfs.basic.mybatisflex.config.FsMybatisFlexConfiguration;

/**
 * Mybatis 常用重用拦截器
 * <p>
 * 拦截器执行一定是：
 * WriteInterceptor > DataScopeInterceptor > PaginationInterceptor
 *
 * @author zuihou
 * @date 2018/10/24
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
@MapperScan(basePackages = {"top.fsfsfs.boot", "top.fsfsfs.basic"}, annotationClass = Repository.class)
public class MybatisFlexConfiguration extends FsMybatisFlexConfiguration {

    public MybatisFlexConfiguration(final DatabaseProperties databaseProperties) {
        super(databaseProperties);
        print();
    }

    public static void print() {
        //开启审计功能
        AuditManager.setAuditEnable(true);
        // TODO: 自定义sql打印 或 自定义审计功能，也可结合logback将sql日志输出到独立文件中。 详见https://mybatis-flex.com/zh/core/audit.html
        //设置 SQL 审计收集器
        AuditManager.setMessageCollector(auditMessage ->
                log.info("{} ---- {}ms, row:{}",
                        formatSQL(auditMessage.getFullSql()),
                        auditMessage.getElapsedTime(),
                        auditMessage.getQueryCount())
        );
    }

    public static String formatSQL(String sql) {
        return sql.replaceAll("\\s+", " ").replace("\\r", " ").replace("\\n", " ");
    }

}
