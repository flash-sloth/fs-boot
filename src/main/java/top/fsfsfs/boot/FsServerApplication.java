package top.fsfsfs.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.fsfsfs.basic.validator.annotation.EnableFormValidator;
import top.fsfsfs.boot.common.ServerApplication;

import java.net.UnknownHostException;


/**
 * 基础服务启动类
 *
 * @author zuihou
 * @since 2021-10-08
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@ComponentScan({"top.fsfsfs.boot"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
@EnableFormValidator
public class FsServerApplication extends ServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        start(FsServerApplication.class, args);
    }
}
