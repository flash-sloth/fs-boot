/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.fsfsfs.boot.modules.generator.properties;

import com.mybatisflex.codegen.constant.GenerationStrategyEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.constant.Constants;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 代码生成默认配置
 * @author tangyh
 * @since 2024年06月21日11:09:07
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = CodeCreatorProperties.PREFIX)
public class CodeCreatorProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".generator";
    private PackageRule packageRule = new PackageRule();
    private StrategyRule strategyRule = new StrategyRule();
    private EntityRule entityRule = new EntityRule();
    private VoRule voRule = new VoRule();
    private DtoRule dtoRule = new DtoRule();
    private QueryRule queryRule = new QueryRule();
    private MapperRule mapperRule = new MapperRule();
    private XmlRule xmlRule = new XmlRule();
    private ServiceRule serviceRule = new ServiceRule();
    private ServiceImplRule serviceImplRule = new ServiceImplRule();
    private ControllerRule controllerRule = new ControllerRule();

    /**
     * 代码生成器 包信息 默认规则
     *
     * @author tangyh
     * @since 2021-08-01 16:04
     */
    @Data
    public static class PackageRule {
        /**
         * 代码生成目录。
         */
        private String sourceDir = System.getProperty("user.dir");
        /**
         * 根包。
         */
        private String basePackage = "top.fsfsfs.boot.modules";
        private String author = System.getProperty("user.name");
//    /**
//     * Vo 所在包。
//     */
//    private String voPackage = "vo";
//    /**
//     * Dto 所在包。
//     */
//    private String dtoPackage = "dto";
//    /**
//     * Query 所在包。
//     */
//    private String queryPackage = "query";
//
//    /**
//     * Entity 所在包。
//     */
//    private String entityPackage = "entity";
//    /**
//     * Mapper 所在包。
//     */
//    private String mapperPackage = "mapper";
//
//    /**
//     * Service 所在包。
//     */
//    private String servicePackage = "service";
//
//    /**
//     * ServiceImpl 所在包。
//     */
//    private String serviceImplPackage = "service.impl";
//
//    /**
//     * Controller 所在包。
//     */
//    private String controllerPackage = "controller";
//
//    /**
//     * MapperXml 文件所在位置。
//     */
//    private String mapperXmlPath = "/mapper";
    }


    /**
     * 代码生成器 表策略配置
     *
     * @author tangyh
     * @since 2021-08-01 16:04
     */
    @Data
    public static class StrategyRule {
        /**
         * 数据库表前缀，多个前缀用英文逗号（,） 隔开。
         */
        private String tablePrefix = "fs_";

        /**
         * 乐观锁的字段名称。
         */
        private String versionColumn;
        /**
         * 租户字段的字段名称。
         */
        private String tenantColumn;
        /**
         * 大字段的jdbc类型
         */
        private List<String> largeColumnTypes = Arrays.asList("TEXT", "LONGTEXT");

        /**
         * 是否生成视图映射。
         */
        private Boolean generateForView = true;

        /**
         * 需要忽略的列 全局配置。
         */
        private Set<String> ignoreColumns;
    }

    /**
     * 生成 Vo 的配置。
     *
     * @author tangyh
     * @since 2024年06月18日15:51:07
     */
    @Data
    public static class DtoRule {
        /** 包名 */
        private String packageName = "dto";

        /**
         * DTO 类的前缀。
         */
        private String classPrefix = "";

        /**
         * DTO 类的后缀。
         */
        private String classSuffix = "Dto";

        /**
         * DTO 类的父类，可以自定义一些 BaseEntity 类。
         */
        private Class<?> superClass;

        /** 父类泛型的类型 */
        private Class<?> genericityType;

        /**
         * VO 默认实现的接口。
         */
        private Class<?>[] implInterfaces = {Serializable.class};

        /**
         * 是否使用 Lombok 注解。
         */
        private Boolean withLombok = true;
        /**
         * 是否链式
         */
        private Boolean withChain = true;
        /**
         * 是否使用 Validator 注解。
         */
        private Boolean withValidator = true;

        /**
         *  是否使用 Swagger 注解。
         */
        private Boolean withSwagger = true;

        /**
         * 需要忽略的列 全局配置。
         */
        private Set<String> ignoreColumns = Set.of(SuperEntity.CREATED_AT_FIELD, SuperEntity.CREATED_BY_FIELD,
                SuperEntity.UPDATED_AT_FIELD, SuperEntity.UPDATED_BY_FIELD,
                SuperEntity.DELETED_AT_FIELD, SuperEntity.DELETED_BY_FIELD);
    }

    /**
     * 代码生成器 实体类 默认规则
     *
     * @author tangyh
     * @since 2021-08-01 16:04
     */
    @Data
    public static class EntityRule {
        /** 包名 */
        private String packageName = "entity";
        /**
         * Entity 类的前缀。
         */
        private String classPrefix = "";

        /**
         * Entity 类的后缀。
         */
        private String classSuffix = "";
        /**
         * Entity 类的父类，可以自定义一些 BaseEntity 类。
         */
        private Class<?> superClass = SuperEntity.class;
        /** 父类泛型的类型 */
        private Class<?> genericityType = Long.class;
        /**
         * Entity 默认实现的接口。
         */
        private Class<?>[] implInterfaces = {Serializable.class};
        /**
         * 生成策略
         */
        private GenerationStrategyEnum generationStrategy = GenerationStrategyEnum.OVERWRITE;

        /**
         * Entity 是否使用 Lombok 注解。
         */
        private Boolean withLombok = true;
        /**
         * 是否链式
         */
        private Boolean withChain = true;

        /**
         * Entity 是否使用 Swagger 注解。
         */
        private Boolean withSwagger = false;

        /**
         * 是否生成base类
         */
        private Boolean withBaseClass = true;
        /**
         * 是否总是生成 @Column 注解。
         */
        private Boolean alwaysGenColumnAnnotation = false;
    }


    /**
     * 生成 Query 默认规则
     *
     * @author tangyh
     * @since 2024年06月18日15:51:07
     */
    @Data
    public static class QueryRule {

        /** 包名 */
        private String packageName = "query";

        /**
         * Query 类的前缀。
         */
        private String classPrefix = "";

        /**
         * Query 类的后缀。
         */
        private String classSuffix = "Query";

        /**
         * Query 类的父类，可以自定义一些 BaseEntity 类。
         */
        private Class<?> superClass;

        /** 父类泛型的类型 */
        private Class<?> genericityType;

        /**
         * VO 默认实现的接口。
         */
        private Class<?>[] implInterfaces = {Serializable.class};

        /**
         * Entity 是否使用 Lombok 注解。
         */
        private Boolean withLombok = true;
        /**
         * 是否链式
         */
        private Boolean withChain = true;
        /**
         * Entity 是否使用 Swagger 注解。
         */
        private Boolean withSwagger = true;
        /** 导入注解 */
        private Boolean withExcel = true;
    }


    /**
     * 生成 Vo 的默认规则。
     *
     * @author tangyh
     * @since 2024年06月18日15:51:07
     */
    @Data
    public static class VoRule {

        /**
         * 代码生成目录，当未配置时，使用 PackageConfig 的配置
         */
        private String packageName = "vo";

        /**
         * VO 类的前缀。
         */
        private String classPrefix = "";

        /**
         * VO 类的后缀。
         */
        private String classSuffix = "Vo";

        /**
         * VO 类的父类，可以自定义一些 BaseEntity 类。
         */
        private Class<?> superClass;

        /** 父类泛型的类型 */
        private Class<?> genericityType;
        /**
         * VO 默认实现的接口。
         */
        private Class<?>[] implInterfaces = {Serializable.class};

        /**
         * 是否使用 Lombok 注解。
         */
        private Boolean withLombok = true;
        /**
         * 是否链式
         */
        private Boolean withChain = true;
        /**
         *  是否使用 Swagger 注解。
         */
        private Boolean withSwagger = true;
        /** 导出(@Excel)注解 */
        private Boolean withExcel = true;
    }

    /**
     * 生成 Mapper 的默认规则。
     *
     * @author tangyh
     * @since 2024年06月18日15:51:07
     */
    @Data
    public static class MapperRule {
        /**
         * 代码生成目录
         */
        private String packageName = "mapper";

        /**
         *  类的前缀。
         */
        private String classPrefix = "";

        /**
         *  类的后缀。
         */
        private String classSuffix = "Mapper";

        /**
         *  类的父类
         */
        private Class<?> superClass = SuperMapper.class;
    }

    @Data
    public static class ServiceRule {
        /**
         * 代码生成目录
         */
        private String packageName = "service";

        /**
         *  类的前缀。
         */
        private String classPrefix = "";

        /**
         *  类的后缀。
         */
        private String classSuffix = "Service";

        /**
         *  类的父类，
         */
        private Class<?> superClass = SuperService.class;
    }

    @Data
    public static class ServiceImplRule {
        /**
         * 代码生成目录
         */
        private String packageName = "service.impl";

        /**
         *  类的前缀。
         */
        private String classPrefix = "";

        /**
         *  类的后缀。
         */
        private String classSuffix = "ServiceImpl";

        /**
         *  类的父类
         */
        private Class<?> superClass = SuperServiceImpl.class;
    }

    @Data
    public static class ControllerRule {
        /**
         * 代码生成目录
         */
        private String packageName = "controller";

        /**
         *  RequestMapping注解，访问路径的前缀。
         */
        private String requestMappingPrefix;

        /**
         *  类的前缀。
         */
        private String classPrefix = "";

        /**
         *  类的后缀。
         */
        private String classSuffix = "Controller";

        /**
         *  类的父类
         */
        private Class<?> superClass = SuperController.class;

        /**
         * 在Controller类中生成CRUD方法。
         */
        private Boolean withCrud = false;

        /**
         * 生成 REST 风格的 Controller。
         */
        private Boolean restStyle = true;
    }

    @Data
    public static class XmlRule {
        /**
         * 代码生成目录
         */
        private String path = "mapper";

        /**
         *  前缀。
         */
        private String filePrefix = "";

        /**
         *  后缀。
         */
        private String fileSuffix = "Mapper";
    }

}
