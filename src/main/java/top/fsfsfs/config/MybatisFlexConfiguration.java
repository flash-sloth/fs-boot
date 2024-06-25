package top.fsfsfs.config;

import com.mybatisflex.spring.boot.MybatisFlexProperties;
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
 *
 * @author zuihou
 * @since 2018/10/24
 */
@Configuration
@Slf4j
@EnableConfigurationProperties({DatabaseProperties.class})
@MapperScan(basePackages = "top.fsfsfs", annotationClass = Repository.class)
public class MybatisFlexConfiguration extends FsMybatisFlexConfiguration {
    public MybatisFlexConfiguration(final DatabaseProperties databaseProperties, MybatisFlexProperties mybatisFlexProperties) {
        super(databaseProperties, mybatisFlexProperties);
    }

}
