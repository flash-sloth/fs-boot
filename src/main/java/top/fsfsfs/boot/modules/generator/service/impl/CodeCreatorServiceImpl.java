package top.fsfsfs.boot.modules.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.fsg.uid.UidGenerator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.StrategyConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.codegen.constant.GenTypeConst;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.dialect.TsTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.mybatisflex.codegen.generator.IGenerator;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.boot.modules.generator.GeneratorUtil;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.entity.CodeCreatorColumn;
import top.fsfsfs.boot.modules.generator.entity.type.ControllerConfig;
import top.fsfsfs.boot.modules.generator.entity.type.DtoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.EntityConfig;
import top.fsfsfs.boot.modules.generator.entity.type.MapperConfig;
import top.fsfsfs.boot.modules.generator.entity.type.PackageConfig;
import top.fsfsfs.boot.modules.generator.entity.type.QueryConfig;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceConfig;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplConfig;
import top.fsfsfs.boot.modules.generator.entity.type.VoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.XmlConfig;
import top.fsfsfs.boot.modules.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.DtoRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.EntityRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.PackageRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.QueryRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.VoRule;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorService;
import top.fsfsfs.boot.modules.generator.vo.TableImportDto;
import top.fsfsfs.util.utils.CollHelper;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.io.File;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 代码生成 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableConfigurationProperties(CodeCreatorProperties.class)
public class CodeCreatorServiceImpl extends SuperServiceImpl<CodeCreatorMapper, CodeCreator> implements CodeCreatorService {
    private final UidGenerator uidGenerator;
    private final CodeCreatorProperties codeCreatorProperties;
    private final CodeCreatorColumnMapper codeCreatorColumnMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(TableImportDto importDto) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/flash_sloth?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        GeneratorUtil generatorUtil = new GeneratorUtil(dataSource, codeCreatorProperties);
        List<Table> tables = generatorUtil.getTables(importDto.getTableNames());

        CodeCreatorProperties.StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();

        List<CodeCreator> list = new ArrayList<>();
        List<CodeCreatorColumn> columnList = new ArrayList<>();
        for (Table table : tables) {
            CodeCreator codeCreator = buildCodeCreator(importDto, table);
            List<PropertyDesign> propertyDesignList = new ArrayList<>();

            List<Column> allColumns = table.getAllColumns();
            for (int i = 0; i < allColumns.size(); i++) {
                Column column = allColumns.get(i);

                fillCodeCreatorColumn(codeCreator, column, i, columnList);
                fillPropertyDesign(column, strategyRule, propertyDesignList);
                //TODO 其他
            }

            codeCreator.setPropertyDesign(propertyDesignList);
            list.add(codeCreator);
        }

        saveBatch(list);
        codeCreatorColumnMapper.insertBatch(columnList);
        return true;
    }

    private static void fillCodeCreatorColumn(CodeCreator codeCreator, Column column, int i, List<CodeCreatorColumn> columnList) {
        CodeCreatorColumn codeCreatorColumn = new CodeCreatorColumn();
        codeCreatorColumn.setCodeCreatorId(codeCreator.getId());
        codeCreatorColumn.setName(column.getName());
        codeCreatorColumn.setTypeName(column.getRawType());
        codeCreatorColumn.setRemarks(column.getComment());
        codeCreatorColumn.setSize(column.getRawLength());
        codeCreatorColumn.setDigit(column.getRawScale());
        codeCreatorColumn.setIsPk(column.isPrimaryKey());
        codeCreatorColumn.setAutoIncrement(column.getAutoIncrement());
        codeCreatorColumn.setIsNullable(column.getNullable() == ResultSetMetaData.columnNullable);
        codeCreatorColumn.setDefValue(column.getPropertyDefaultValue());
        codeCreatorColumn.setWeight(i);
        columnList.add(codeCreatorColumn);
    }

    private static void fillPropertyDesign(Column column, CodeCreatorProperties.StrategyRule strategyRule, List<PropertyDesign> propertyDesignList) {
        PropertyDesign propertyDesign = new PropertyDesign();
        propertyDesign.setProperty(column.buildPropertyName());
        propertyDesign.setName(column.getName());
        propertyDesign.setPropertyType(column.getPropertyType());
        propertyDesign.setPropertySimpleType(column.getPropertySimpleType());
        propertyDesign.setTsType(column.getTsType());
        propertyDesign.setSwaggerDescription(column.getSwaggerComment());
        propertyDesign.setRequired(column.getNullable() == ResultSetMetaData.columnNoNulls);
        propertyDesign.setVersion(StrUtil.equals(column.getName(), strategyRule.getVersionColumn()));
        propertyDesign.setLarge(CollUtil.contains(strategyRule.getLargeColumnTypes(), column.getRawType()));
        propertyDesign.setTenant(StrUtil.equals(column.getName(), strategyRule.getTenantColumn()));
        propertyDesignList.add(propertyDesign);
    }

    @NotNull
    private CodeCreator buildCodeCreator(TableImportDto importDto, Table table) {
        CodeCreator codeCreator = new CodeCreator();
        codeCreator.setId(uidGenerator.getUid());
        codeCreator.setTableName(table.getName());
        codeCreator.setTableDescription(table.getComment());
        codeCreator.setDsId(importDto.getDsId());
        fillPackageConfig(table, codeCreator);
        fillEntityConfig(table, codeCreator);
        fillVoConfig(table, codeCreator);
        fillDtoConfig(table, codeCreator);
        fillQueryConfig(table, codeCreator);
        fillMapperConfig(table, codeCreator);
        fillXmlConfig(table, codeCreator);
        fillServiceConfig(table, codeCreator);
        fillServiceImplConfig(table, codeCreator);
        fillControllerConfig(table, codeCreator);
        // TODO 其他
        return codeCreator;
    }

    private void fillControllerConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ControllerRule controllerRule = codeCreatorProperties.getControllerRule();
        ControllerConfig controllerConfig = new ControllerConfig();
        controllerConfig.setPackageName(controllerRule.getPackageName())
                .setName(table.buildControllerClassName())
                .setSuperClassName(controllerRule.getSuperClass() != null ? controllerRule.getSuperClass().getName() : "")
                .setRequestMappingPrefix(controllerRule.getRequestMappingPrefix())
                .setWithCrud(controllerRule.getWithCrud())
                .setRestStyle(controllerRule.getRestStyle())
        ;
        codeCreator.setControllerConfig(controllerConfig);
    }

    private void fillServiceImplConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ServiceImplRule serviceRule = codeCreatorProperties.getServiceImplRule();
        ServiceImplConfig serviceConfig = new ServiceImplConfig();
        serviceConfig.setPackageName(serviceRule.getPackageName())
                .setName(table.buildServiceImplClassName())
                .setSuperClassName(serviceRule.getSuperClass() != null ? serviceRule.getSuperClass().getName() : "")
        ;
        codeCreator.setServiceImplConfig(serviceConfig);
    }

    private void fillServiceConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ServiceRule serviceRule = codeCreatorProperties.getServiceRule();
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setPackageName(serviceRule.getPackageName())
                .setName(table.buildServiceClassName())
                .setSuperClassName(serviceRule.getSuperClass() != null ? serviceRule.getSuperClass().getName() : "")
        ;
        codeCreator.setServiceConfig(serviceConfig);
    }

    private void fillXmlConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.XmlRule xmlRule = codeCreatorProperties.getXmlRule();
        XmlConfig xmlConfig = new XmlConfig();
        xmlConfig.setPath(xmlRule.getPath())
                .setName(table.buildMapperXmlFileName())
        ;
        codeCreator.setXmlConfig(xmlConfig);
    }

    private void fillMapperConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.MapperRule mapperRule = codeCreatorProperties.getMapperRule();
        MapperConfig mapperConfig = new MapperConfig();
        mapperConfig.setPackageName(mapperRule.getPackageName())
                .setName(table.buildMapperClassName())
                .setSuperClassName(mapperRule.getSuperClass() != null ? mapperRule.getSuperClass().getName() : "")
        ;
        codeCreator.setMapperConfig(mapperConfig);
    }

    private void fillQueryConfig(Table table, CodeCreator codeCreator) {
        QueryRule queryRule = codeCreatorProperties.getQueryRule();
        QueryConfig queryConfig = new QueryConfig();
        queryConfig.setPackageName(queryRule.getPackageName())
                .setName(table.buildQueryClassName())
                .setDescription(table.getSwaggerComment())
                .setSuperClassName(queryRule.getSuperClass() != null ? queryRule.getSuperClass().getName() : "")
                .setGenericityTypeName(queryRule.getGenericityType() != null ? queryRule.getGenericityType().getName() : "")
                .setWithLombok(queryRule.getWithLombok())
                .setWithChain(queryRule.getWithChain())
                .setWithSwagger(queryRule.getWithSwagger())
                .setWithExcel(queryRule.getWithExcel())
        ;
        Class<?>[] implInterfaces = queryRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            queryConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            queryConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setQueryConfig(queryConfig);
    }

    private void fillDtoConfig(Table table, CodeCreator codeCreator) {
        DtoRule dtoRule = codeCreatorProperties.getDtoRule();
        DtoConfig dtoConfig = new DtoConfig();
        dtoConfig.setPackageName(dtoRule.getPackageName())
                .setName(table.buildDtoClassName())
                .setDescription(table.getSwaggerComment())
                .setSuperClassName(dtoRule.getSuperClass() != null ? dtoRule.getSuperClass().getName() : "")
                .setGenericityTypeName(dtoRule.getGenericityType() != null ? dtoRule.getGenericityType().getName() : "")
                .setWithLombok(dtoRule.getWithLombok())
                .setWithChain(dtoRule.getWithChain())
                .setWithSwagger(dtoRule.getWithSwagger())
                .setWithValidator(dtoRule.getWithValidator())
                .setIgnoreColumns(dtoRule.getIgnoreColumns())
        ;
        Class<?>[] implInterfaces = dtoRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            dtoConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            dtoConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setDtoConfig(dtoConfig);
    }

    private void fillVoConfig(Table table, CodeCreator codeCreator) {
        VoRule voRule = codeCreatorProperties.getVoRule();
        VoConfig voConfig = new VoConfig();
        voConfig.setPackageName(voRule.getPackageName())
                .setName(table.buildVoClassName())
                .setDescription(table.getSwaggerComment())
                .setSuperClassName(voRule.getSuperClass() != null ? voRule.getSuperClass().getName() : "")
                .setGenericityTypeName(voRule.getGenericityType() != null ? voRule.getGenericityType().getName() : "")
                .setWithLombok(voRule.getWithLombok())
                .setWithChain(voRule.getWithChain())
                .setWithSwagger(voRule.getWithSwagger())
                .setWithExcel(voRule.getWithExcel())
        ;
        Class<?>[] implInterfaces = voRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            voConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            voConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setVoConfig(voConfig);
    }

    private void fillEntityConfig(Table table, CodeCreator codeCreator) {
        EntityRule entityRule = codeCreatorProperties.getEntityRule();
        EntityConfig entityConfig = new EntityConfig();
        entityConfig
                .setPackageName(entityRule.getPackageName())
                .setName(table.buildEntityClassName())
                .setDescription(table.getSwaggerComment())
                .setSuperClassName(entityRule.getSuperClass() != null ? entityRule.getSuperClass().getName() : "")
                .setGenericityTypeName(entityRule.getGenericityType() != null ? entityRule.getGenericityType().getName() : "")
                .setWithLombok(entityRule.getWithLombok())
                .setWithChain(entityRule.getWithChain())
                .setWithBaseClass(entityRule.getWithBaseClass())
                .setWithSwagger(entityRule.getWithSwagger())
                .setAlwaysGenColumnAnnotation(entityRule.getAlwaysGenColumnAnnotation())
        ;
//        Class<?>[] implInterfaces = entityRule.getImplInterfaces();
//        if (implInterfaces != null) {
//            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
//            entityConfig.setImplInterfaceNames(implInterfaceNames);
//        } else {
//            entityConfig.setImplInterfaceNames(new String[0]);
//        }
        codeCreator.setEntityConfig(entityConfig);
    }

    private void fillPackageConfig(Table table, CodeCreator codeCreator) {
        PackageConfig packageConfig = new PackageConfig();
        PackageRule packageRule = codeCreatorProperties.getPackageRule();
        packageConfig.setSourceDir(packageRule.getSourceDir()).setBasePackage(packageRule.getBasePackage()).setAuthor(packageRule.getAuthor());
        codeCreator.setPackageConfig(packageConfig);
    }

    @Override
    public List<Tree<Long>> preview(List<Long> ids) {


        List<Table> tables = getTables(ids);

        if (tables == null || tables.isEmpty()) {
            log.error("table not found.");
            return Collections.emptyList();
        } else {
            log.info("find tables: {}", tables.stream().map(Table::getName).collect(Collectors.toSet()));
        }

        Map<String, Preview> cache = new HashMap<>();
        List<Preview> previews = new ArrayList<>();

        Map<String, Map<String, String>> tableMap = new HashMap(tables.size());
        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            Collection<IGenerator> generators = GeneratorFactory.getGenerators();
            Map<String, String> codeMap = new HashMap(generators.size());
            for (IGenerator generator : generators) {
                codeMap.put(generator.getGenType(), generator.preview(table, table.getGlobalConfig()));
            }

            buildCodeTree(table, previews, cache, codeMap, i);
            tableMap.put(table.getName(), codeMap);
        }
        log.info("Code is generated successfully.");

        log.info("size ={} ", previews.size());
        List<Tree<Long>> treeList = FsTreeUtil.build(previews, new PreviewNodeParser());
        log.info("treeList ={} ", treeList);
        return treeList;
    }

    public static class PreviewNodeParser implements NodeParser<Preview, Long> {
        @Override
        public void parse(Preview treeNode, Tree<Long> tree) {
            Map<String, Object> map = BeanUtil.beanToMap(treeNode);
            tree.putAll(map);

            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
        }
    }

    public void buildCodeTree(Table table, List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex) {

        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(GLOBAL_CONFIG_KEY);
        PackageConfig packageConfig = codeCreator.getPackageConfig();
        ControllerConfig controllerConfig = codeCreator.getControllerConfig();
        ServiceConfig serviceConfig = codeCreator.getServiceConfig();
        ServiceImplConfig serviceImplConfig = codeCreator.getServiceImplConfig();
        MapperConfig mapperConfig = codeCreator.getMapperConfig();
        EntityConfig entityConfig = codeCreator.getEntityConfig();
        VoConfig voConfig = codeCreator.getVoConfig();
        DtoConfig dtoConfig = codeCreator.getDtoConfig();
        QueryConfig queryConfig = codeCreator.getQueryConfig();
        XmlConfig xmlConfig = codeCreator.getXmlConfig();

        Preview root = cache.get(packageConfig.getSourceDir());
        if (root == null) {
            root = new Preview();
            root.setPath(packageConfig.getSourceDir())
                    .setIsReadonly(true)
                    .setType("project")
                    .setWeight(tableIndex + 1)
                    .setId(uidGenerator.getUid())
                    .setName("fs-boot")
                    .setParentId(null);
            cache.put(packageConfig.getSourceDir(), root);

//            root.setWeight(weightMap.getOrDefault("", 0) + 1);
            previews.add(root);
        }

        String javaDirKey = StrPool.SRC_MAIN_JAVA;
        Preview javaDir = cache.get(javaDirKey);
        if (javaDir == null) {
            javaDir = new Preview();
            javaDir.setPath(root.getPath() + File.separator + StrUtil.replace(javaDirKey, "/", File.separator))
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(javaDirKey)
                    .setParentId(root.getId());
            cache.put(javaDirKey, javaDir);
//            javaDir.setWeight(javaDir.getWeight() + 1);
            previews.add(javaDir);
        }

        String resourceDirKey = StrPool.SRC_MAIN_RESOURCES;
        Preview resourceDir = cache.get(resourceDirKey);
        if (resourceDir == null) {
            resourceDir = new Preview();
            resourceDir.setPath(root.getPath() + File.separator + resourceDirKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(2)
                    .setName(resourceDirKey)
                    .setParentId(root.getId());

            cache.put(resourceDirKey, resourceDir);
//            resourceDir.setWeight(resourceDir.getWeight() + 1);
            previews.add(resourceDir);
        }

        String packageKey = packageConfig.getBasePackage() + "." + packageConfig.getModule();
        Preview backPackageDir = cache.get(packageKey);
        if (backPackageDir == null) {
            backPackageDir = new Preview();
            backPackageDir.setPath(javaDir.getPath() + File.separator + StrUtil.replace(packageKey, ".", File.separator))
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(packageKey)
                    .setParentId(javaDir.getId());
            cache.put(packageKey, backPackageDir);
//            backPackageDir.setWeight(backPackageDir.getWeight() + 1);
            previews.add(backPackageDir);
        }

        buildControlerLayer(previews, cache, codeMap, tableIndex, controllerConfig, backPackageDir);
        buildServiceLayer(previews, cache, codeMap, tableIndex, serviceConfig, backPackageDir);
        buildServiceImplLayer(previews, cache, codeMap, tableIndex, serviceImplConfig, backPackageDir);
        buildMapperLayer(previews, cache, codeMap, tableIndex, mapperConfig, backPackageDir);
        buildEntityLayer(previews, cache, codeMap, tableIndex, entityConfig, backPackageDir);
        buildLayer(previews, cache, backPackageDir, tableIndex, 6, voConfig.getPackageName(), voConfig.getName(), codeMap.get(GenTypeConst.VO));
        buildLayer(previews, cache, backPackageDir, tableIndex, 7, dtoConfig.getPackageName(), dtoConfig.getName(), codeMap.get(GenTypeConst.DTO));
        buildLayer(previews, cache, backPackageDir, tableIndex, 8, queryConfig.getPackageName(), queryConfig.getName(), codeMap.get(GenTypeConst.QUERY));
//        buildVoLayer(previews, cache, codeMap, tableIndex, voConfig, backPackageDir);
//        buildDtoLayer(previews, cache, codeMap, tableIndex, dtoConfig, backPackageDir);
//        buildQueryLayer(previews, cache, codeMap, tableIndex, queryConfig, backPackageDir);

        buildXml(previews, cache, codeMap, tableIndex, xmlConfig, resourceDir);
    }


    private void buildControlerLayer(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, ControllerConfig controllerConfig, Preview backPackageDir) {
        Preview controllerDir = cache.get(controllerConfig.getPackageName());
        if (controllerDir == null) {
            controllerDir = new Preview();
            controllerDir.setPath(backPackageDir.getPath() + File.separator + controllerConfig.getPackageName())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(controllerConfig.getPackageName())
                    .setParentId(backPackageDir.getId());
            cache.put(controllerConfig.getPackageName(), controllerDir);
//            controllerDir.setWeight(controllerDir.getWeight() + 1);
            previews.add(controllerDir);
        }

        Preview controllerFile = new Preview();
        controllerFile.setPath(controllerDir.getPath() + File.separator + controllerConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.CONTROLLER))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(controllerConfig.getName())
                .setParentId(controllerDir.getId());
        cache.put(controllerConfig.getName(), controllerFile);
//            controllerDir.setWeight(controllerDir.getWeight() + 1);
        previews.add(controllerFile);
    }

    private void buildServiceLayer(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, ServiceConfig serviceConfig, Preview backPackageDir) {
        Preview serviceDir = cache.get(serviceConfig.getPackageName());
        if (serviceDir == null) {
            serviceDir = new Preview();
            serviceDir.setPath(backPackageDir.getPath() + File.separator + serviceConfig.getPackageName())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 2)
                    .setName(serviceConfig.getPackageName())
                    .setParentId(backPackageDir.getId());
            cache.put(serviceConfig.getPackageName(), serviceDir);
            previews.add(serviceDir);
        }

        Preview serviceFile = new Preview();
        serviceFile.setPath(serviceDir.getPath() + File.separator + serviceConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.SERVICE))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(serviceConfig.getName())
                .setParentId(serviceDir.getId());
        cache.put(serviceConfig.getName(), serviceFile);
        previews.add(serviceFile);
    }

    private void buildServiceImplLayer(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, ServiceImplConfig serviceImplConfig, Preview backPackageDir) {
        Preview serviceImplDir = cache.get(serviceImplConfig.getPackageName());
        if (serviceImplDir == null) {
            serviceImplDir = new Preview();
            serviceImplDir.setPath(backPackageDir.getPath() + File.separator + serviceImplConfig.getPackageName())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 3)
                    .setName(serviceImplConfig.getPackageName())
                    .setParentId(backPackageDir.getId());
            cache.put(serviceImplConfig.getPackageName(), serviceImplDir);
            previews.add(serviceImplDir);
        }

        Preview serviceImplFile = new Preview();
        serviceImplFile.setPath(serviceImplDir.getPath() + File.separator + serviceImplConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.SERVICE_IMPL))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(serviceImplConfig.getName())
                .setParentId(serviceImplDir.getId());
//        cache.put(serviceImplConfig.getName(), serviceFile);
        previews.add(serviceImplFile);
    }

    private void buildMapperLayer(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, MapperConfig mapperConfig, Preview backPackageDir) {
        Preview mapperDir = cache.get(mapperConfig.getPackageName());
        if (mapperDir == null) {
            mapperDir = new Preview();
            mapperDir.setPath(backPackageDir.getPath() + File.separator + mapperConfig.getPackageName())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 4)
                    .setName(mapperConfig.getPackageName())
                    .setParentId(backPackageDir.getId());
            cache.put(mapperConfig.getPackageName(), mapperDir);
            previews.add(mapperDir);
        }

        Preview mapperFile = new Preview();
        mapperFile.setPath(mapperDir.getPath() + File.separator + mapperConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.MAPPER))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(mapperConfig.getName() + ".java")
                .setParentId(mapperDir.getId());
        previews.add(mapperFile);
    }

    private void buildXml(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, XmlConfig xmlConfig, Preview resourceDir) {
        Preview xmlDir = cache.get(resourceDir.getPath() + File.separator + xmlConfig.getPath());
        if (xmlDir == null) {
            xmlDir = new Preview();
            xmlDir.setPath(resourceDir.getPath() + File.separator + xmlConfig.getPath())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(xmlConfig.getPath())
                    .setParentId(resourceDir.getId());
            cache.put(xmlConfig.getPath(), xmlDir);
//            xmlDir.setWeight(xmlDir.getWeight() + 1);
            previews.add(xmlDir);
        }

        Preview xmlFile = new Preview();
        xmlFile.setPath(xmlDir.getPath() + File.separator + xmlConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.MAPPER_XML))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(xmlConfig.getName() + ".xml")
                .setParentId(xmlDir.getId());
        previews.add(xmlFile);
    }

    private void buildLayer(List<Preview> previews, Map<String, Preview> cache, Preview backPackageDir,
                            int tableIndex, int dirIndex, String packageName, String name, String content) {
        Preview layerDir = cache.get(packageName);
        if (layerDir == null) {
            layerDir = new Preview();
            layerDir.setPath(backPackageDir.getPath() + File.separator + packageName)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + dirIndex)
                    .setName(packageName)
                    .setParentId(backPackageDir.getId());
            cache.put(packageName, layerDir);
            previews.add(layerDir);
        }

        Preview codeFile = new Preview();
        codeFile.setPath(layerDir.getPath() + File.separator + name)
                .setType("file")
                .setIsReadonly(false)
                .setContent(content)
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(name)
                .setParentId(layerDir.getId());
        previews.add(codeFile);
    }

    private void buildEntityLayer(List<Preview> previews, Map<String, Preview> cache, Map<String, String> codeMap, int tableIndex, EntityConfig entityConfig, Preview backPackageDir) {
        Preview layerDir = cache.get(entityConfig.getPackageName());
        if (layerDir == null) {
            layerDir = new Preview();
            layerDir.setPath(backPackageDir.getPath() + File.separator + entityConfig.getPackageName())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 5)
                    .setName(entityConfig.getPackageName())
                    .setParentId(backPackageDir.getId());
            cache.put(entityConfig.getPackageName(), layerDir);
            previews.add(layerDir);
        }

        Preview codeFile = new Preview();
        codeFile.setPath(layerDir.getPath() + File.separator + entityConfig.getName())
                .setType("file")
                .setIsReadonly(false)
                .setContent(codeMap.get(GenTypeConst.ENTITY))
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(entityConfig.getName())
                .setParentId(layerDir.getId());
        previews.add(codeFile);
    }

    @Data
    @Accessors(chain = true)
    public static class Preview extends TreeNode<Long> {
        //        private Long id;
//        private Integer weight;
        //        private Long parentId;
//        private String name;
        private String path;
        private String type;
        private String content;
        private Boolean isReadonly;
    }

    private final static String GLOBAL_CONFIG_KEY = "$codeCreator";

    public List<Table> getTables(List<Long> ids) {

        List<CodeCreator> codeCreatorList = listByIds(ids);
        if (CollUtil.isEmpty(codeCreatorList)) {
            throw BizException.wrap("请选择需要预览的表");
        }

        List<Table> tables = new ArrayList<>();
        for (CodeCreator codeCreator : codeCreatorList) {
            GlobalConfig globalConfig = buildGlobalConfig(codeCreator);

            QueryWrapper wrapper = QueryWrapper.create().eq(CodeCreatorColumn::getCodeCreatorId, codeCreator.getId()).orderBy(CodeCreatorColumn::getWeight, true);
            List<CodeCreatorColumn> columnList = codeCreatorColumnMapper.selectListByQuery(wrapper);

            List<PropertyDesign> propertyDesignList = codeCreator.getPropertyDesign();
            Map<String, PropertyDesign> propertyDesignMap = CollUtil.isNotEmpty(propertyDesignList) ? CollHelper.uniqueIndex(propertyDesignList, PropertyDesign::getName, item -> item) : Collections.emptyMap();

            Table table = new Table();
            table.setGlobalConfig(globalConfig);
            table.setEntityConfig(globalConfig.getEntityConfig());
            table.setName(codeCreator.getTableName());
            table.setComment(codeCreator.getTableDescription());

            for (CodeCreatorColumn creatorColumn : columnList) {
                if (creatorColumn.getIsPk()) {
                    table.addPrimaryKey(creatorColumn.getName());
                }
                Column column = new Column();
                column.setName(creatorColumn.getName());

                column.setRawType(creatorColumn.getTypeName());
                column.setRawLength(creatorColumn.getSize());
                column.setRawScale(creatorColumn.getDigit());

                column.setAutoIncrement(creatorColumn.getAutoIncrement());

                column.setNullable(creatorColumn.getIsNullable() ? ResultSetMetaData.columnNullable : ResultSetMetaData.columnNoNulls);
                //注释
                column.setComment(creatorColumn.getRemarks());

                PropertyDesign propertyDesign = propertyDesignMap.get(creatorColumn.getName());
                if (propertyDesign != null) {
                    column.setPropertyType(propertyDesign.getPropertyType());
                    column.setTsType(propertyDesign.getTsType());
                } else {
                    column.setPropertyType(JdbcTypeMapping.getType(creatorColumn.getTypeName(), table, column));
                    column.setTsType(TsTypeMapping.getType(column.getRawType(), creatorColumn.getTypeName(), table, column));

                }
                table.addColumn(column);
            }

            tables.add(table);
        }
        return tables;
    }

    @NotNull
    @SneakyThrows
    private GlobalConfig buildGlobalConfig(CodeCreator codeCreator) {
        CodeCreatorProperties.StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();
        EntityRule entityRule = codeCreatorProperties.getEntityRule();
        VoRule voRule = codeCreatorProperties.getVoRule();
        DtoRule dtoRule = codeCreatorProperties.getDtoRule();
        QueryRule queryRule = codeCreatorProperties.getQueryRule();
        CodeCreatorProperties.ControllerRule controllerRule = codeCreatorProperties.getControllerRule();

        PackageConfig packageConfig = codeCreator.getPackageConfig();
        ControllerConfig controllerConfig = codeCreator.getControllerConfig();
        ServiceConfig serviceConfig = codeCreator.getServiceConfig();
        ServiceImplConfig serviceImplConfig = codeCreator.getServiceImplConfig();
        MapperConfig mapperConfig = codeCreator.getMapperConfig();
        EntityConfig entityConfig = codeCreator.getEntityConfig();
        VoConfig voConfig = codeCreator.getVoConfig();
        DtoConfig dtoConfig = codeCreator.getDtoConfig();
        QueryConfig queryConfig = codeCreator.getQueryConfig();
        XmlConfig xmlConfig = codeCreator.getXmlConfig();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setCustomConfig(GLOBAL_CONFIG_KEY, codeCreator);
        globalConfig.setJdkVersion(17);

        globalConfig.getJavadocConfig()
                .setTableCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setTableSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""));

        String basePackage = packageConfig.getBasePackage() + StrPool.DOT + packageConfig.getModule();
        globalConfig.getPackageConfig().
                setSourceDir(packageConfig.getSourceDir() + StrPool.SLASH + StrPool.SRC_MAIN_JAVA)
                .setBasePackage(basePackage)
                .setVoPackage(basePackage + StrPool.DOT + voConfig.getPackageName())
                .setDtoPackage(basePackage + StrPool.DOT + dtoConfig.getPackageName())
                .setQueryPackage(basePackage + StrPool.DOT + queryConfig.getPackageName())
                .setEntityPackage(basePackage + StrPool.DOT + entityConfig.getPackageName())
                .setMapperPackage(basePackage + StrPool.DOT + mapperConfig.getPackageName())
                .setMapperXmlPath(packageConfig.getSourceDir() + StrPool.SLASH + StrPool.SRC_MAIN_RESOURCES + StrPool.SLASH + xmlConfig.getPath())
                .setServicePackage(basePackage + StrPool.DOT + serviceConfig.getPackageName())
                .setServiceImplPackage(basePackage + StrPool.DOT + serviceImplConfig.getPackageName())
                .setControllerPackage(basePackage + StrPool.DOT + controllerConfig.getPackageName());

        StrategyConfig strategyConfig = globalConfig.getStrategyConfig();
        BeanUtil.copyProperties(strategyRule, strategyConfig);

        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));


        globalConfig.enableEntity()
                .setSuperClass(Class.forName(entityConfig.getSuperClassName()))
                .setGenericityType(entityRule.getGenericityType())
                .setImplInterfaces(entityRule.getImplInterfaces())
                .setWithLombok(entityConfig.getWithLombok())
                .setWithSwagger(entityConfig.getWithSwagger())
                .setSwaggerVersion(com.mybatisflex.codegen.config.EntityConfig.SwaggerVersion.DOC)
                .setWithActiveRecord(false)
//                .setWithChain(entityConfig.getWithChain())
                .setWithBaseClassEnable(entityConfig.getWithBaseClass())
                .setAlwaysGenColumnAnnotation(entityConfig.getAlwaysGenColumnAnnotation())
        ;

        globalConfig.enableVo()
                .setSuperClass(Class.forName(voConfig.getSuperClassName()))
                .setGenericityType(voRule.getGenericityType())
                .setImplInterfaces(voRule.getImplInterfaces())
                .setWithLombok(voConfig.getWithLombok())
                .setWithSwagger(voConfig.getWithSwagger())
                .setWithExcel(voConfig.getWithExcel())
                .setSwaggerVersion(com.mybatisflex.codegen.config.EntityConfig.SwaggerVersion.DOC)
        ;

        globalConfig.enableQuery();
        globalConfig.enableMapper();
        globalConfig.enableMapperXml();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();
        globalConfig.enableController();

        return globalConfig;
    }


}
