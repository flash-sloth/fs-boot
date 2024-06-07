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
 * @since 2018/10/24
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
@MapperScan(basePackages = {"top.fsfsfs.boot", "top.fsfsfs.basic"}, annotationClass = Repository.class)
public class MybatisFlexConfiguration extends FsMybatisFlexConfiguration {

    public MybatisFlexConfiguration(final DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

}
