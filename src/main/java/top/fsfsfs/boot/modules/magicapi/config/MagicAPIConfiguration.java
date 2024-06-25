package top.fsfsfs.boot.modules.magicapi.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.FlexGlobalConfig;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import top.fsfsfs.boot.modules.magicapi.interceptor.CustomRequestInterceptor;
import top.fsfsfs.boot.modules.magicapi.interceptor.CustomUIAuthorizationInterceptor;
import top.fsfsfs.boot.modules.magicapi.provider.*;
import top.fsfsfs.boot.modules.magicapi.scripts.CustomFunction;
import top.fsfsfs.boot.modules.magicapi.scripts.CustomFunctionExtension;
import top.fsfsfs.boot.modules.magicapi.scripts.CustomModule;
import org.ssssssss.magicapi.datasource.model.MagicDynamicDataSource;
import org.ssssssss.magicapi.modules.db.provider.PageProvider;

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

/**
 * magic-api 配置类
 * 以下只配置了多数据源
 * 其它如果有需要可以自行放开 // @Bean 注释查看效果
 */
@Configuration
@Slf4j
public class MagicAPIConfiguration {

	@Resource
	private Environment environment;

	final static  String prefixMaster = "mybatis-flex.datasource.master";
	final static  String prefixSlave = "mybatis-flex.datasource.slave";

	//@Primary  //标记主数据源，当存在多个DataSource Bean时，默认使用该数据源
	//@Bean(name = "master")  //创建一个名为"master"的DataSource Bean
	//@Qualifier("master")
	//@ConfigurationProperties(prefix = "mybatis-flex.datasource.master")  //将属性文件配置注入到DataSource实例中
	public DataSource MasterDataSource(){
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.type(DruidDataSource.class)
				.password(environment.getProperty( prefixMaster+".password") )
				.username(environment.getProperty(prefixMaster+".username") )
				.url(environment.getProperty(prefixMaster+".url") )
				.build();
	}

	//@Bean(name = "slave")
	//@Qualifier("slave")
	//@ConfigurationProperties(prefix = "mybatis-flex.datasource.slave")
	public DataSource SlaveDataSource(){
		return DataSourceBuilder.create()
				.driverClassName("com.mysql.cj.jdbc.Driver")
				.type(DruidDataSource.class)
				.password(environment.getProperty( prefixSlave+".password") )
				.username(environment.getProperty(prefixSlave+".username") )
				.url(environment.getProperty(prefixSlave+".url") )
				.build();
	}

	/**
	 * 配置多数据源
	 *
	 * @see MagicDynamicDataSource
	 */
//	@Bean
	public MagicDynamicDataSource magicDynamicDataSource() {
		MagicDynamicDataSource dynamicDataSource = new MagicDynamicDataSource();
		dynamicDataSource.setDefault(MasterDataSource()); // 设置默认数据源
		dynamicDataSource.add("slave", SlaveDataSource());
		return dynamicDataSource;
	}


	/**
	 * 配置自定义JSON结果
	 */
	// @Bean
	public CustomJsonValueProvider customJsonValueProvider() {
		return new CustomJsonValueProvider();
	}

	/**
	 * 配置分页获取方式
	 */
	// @Bean
	public PageProvider pageProvider() {
		return new CustomPageProvider();
	}

	/**
	 * 自定义UI界面鉴权
	 */
	// @Bean
	public CustomUIAuthorizationInterceptor customUIAuthorizationInterceptor() {
		return new CustomUIAuthorizationInterceptor();
	}

	/**
	 * 自定义请求拦截器（鉴权）
	 */
	// @Bean
	public CustomRequestInterceptor customRequestInterceptor() {
		return new CustomRequestInterceptor();
	}

	/**
	 * 自定义SQL缓存
	 */
	// @Bean
	public CustomSqlCache customSqlCache() {
		return new CustomSqlCache();
	}

	/**
	 * 自定义函数
	 */
	// @Bean
	public CustomFunction customFunction() {
		return new CustomFunction();
	}

	/**
	 * 自定义方法扩展
	 */
	// @Bean
	public CustomFunctionExtension customFunctionExtension() {
		return new CustomFunctionExtension();
	}

	/**
	 * 自定义模块
	 */
	// @Bean
	public CustomModule customModule() {
		return new CustomModule();
	}

	/**
	 * 自定义脚本语言
	 */
	// @Bean
	public CustomLanguageProvider customLanguageProvider() {
		return new CustomLanguageProvider();
	}

	/**
	 * 自定义列名转换
	 */
	// @Bean
	public CustomMapperProvider customMapperProvider() {
		return new CustomMapperProvider();
	}

}
