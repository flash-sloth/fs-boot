package top.fsfsfs.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 服务启动工具类
 *
 * @author zuihou
 */
@Slf4j
public class ServerApplication {

    protected static void start(Class<?> primarySource, String[] args) throws UnknownHostException {
        ConfigurableApplicationContext application = SpringApplication.run(primarySource, args);
        Environment env = application.getEnvironment();
        String msg = """
                                                                                 
                ----------------------------------------------------------
                应用 '{}' 启动成功!
                Swagger文档: http://{}:{}{}/doc.html
                数据库监控:   http://{}:{}/druid
                ----------------------------------------------------------
                """;
        log.info(msg,
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(),
                env.getProperty("server.port"),
                env.getProperty("server.servlet.context-path", ""),
                "127.0.0.1",
                env.getProperty("server.port")
        );
    }
}
