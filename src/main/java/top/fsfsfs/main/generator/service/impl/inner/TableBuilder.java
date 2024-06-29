/*
 * Copyright (c) 2024.  flash-sloth (244387066@qq.com).
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.fsfsfs.main.generator.service.impl.inner;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Multimap;
import io.github.linpeilie.Converter;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.codegen.Generator;
import top.fsfsfs.codegen.config.ControllerConfig;
import top.fsfsfs.codegen.config.DtoConfig;
import top.fsfsfs.codegen.config.EntityConfig;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.config.MapperConfig;
import top.fsfsfs.codegen.config.QueryConfig;
import top.fsfsfs.codegen.config.ServiceConfig;
import top.fsfsfs.codegen.config.ServiceImplConfig;
import top.fsfsfs.codegen.config.StrategyConfig;
import top.fsfsfs.codegen.config.TableConfig;
import top.fsfsfs.codegen.config.VoConfig;
import top.fsfsfs.codegen.dialect.JdbcTypeMapping;
import top.fsfsfs.codegen.dialect.TsTypeMapping;
import top.fsfsfs.codegen.entity.Column;
import top.fsfsfs.codegen.entity.Table;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.entity.CodeCreatorColumn;
import top.fsfsfs.main.generator.entity.type.ControllerDesign;
import top.fsfsfs.main.generator.entity.type.DtoDesign;
import top.fsfsfs.main.generator.entity.type.EntityDesign;
import top.fsfsfs.main.generator.entity.type.MapperDesign;
import top.fsfsfs.main.generator.entity.type.PackageDesign;
import top.fsfsfs.main.generator.entity.type.QueryDesign;
import top.fsfsfs.main.generator.entity.type.ServiceDesign;
import top.fsfsfs.main.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.main.generator.entity.type.VoDesign;
import top.fsfsfs.main.generator.entity.type.XmlDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties.DtoRule;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties.EntityRule;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties.QueryRule;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties.StrategyRule;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties.VoRule;
import top.fsfsfs.util.utils.ClassUtils;
import top.fsfsfs.util.utils.CollHelper;

import javax.sql.DataSource;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 代码生成
 * @author tangyh
 * @since 2024/6/21 12:46
 */
@Slf4j
@AllArgsConstructor
public class TableBuilder {
    private DataSource dataSource;
    private CodeCreatorProperties codeCreatorProperties;
    private final static Converter CONVERTER = new Converter();
    public final static String GLOBAL_CONFIG_KEY = "$codeCreator";

    public TableBuilder(CodeCreatorProperties codeCreatorProperties) {
        this.codeCreatorProperties = codeCreatorProperties;
    }

    public List<Table> whenImportGetTable(Set<String> tableNames) {
        StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();
        CodeCreatorProperties.PackageRule packageRule = codeCreatorProperties.getPackageRule();
        EntityRule entityRule = codeCreatorProperties.getEntityRule();
        QueryRule queryRule = codeCreatorProperties.getQueryRule();
        VoRule voRule = codeCreatorProperties.getVoRule();
        DtoRule dtoRule = codeCreatorProperties.getDtoRule();
        CodeCreatorProperties.ControllerRule controllerRule = codeCreatorProperties.getControllerRule();
        CodeCreatorProperties.ServiceRule serviceRule = codeCreatorProperties.getServiceRule();
        CodeCreatorProperties.ServiceImplRule serviceImplRule = codeCreatorProperties.getServiceImplRule();
        CodeCreatorProperties.MapperRule mapperRule = codeCreatorProperties.getMapperRule();
        CodeCreatorProperties.XmlRule xmlRule = codeCreatorProperties.getXmlRule();

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setJdkVersion(17);

        globalConfig.getJavadocConfig()
                .setTableCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setTableSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setAuthor(packageRule.getAuthor())
        ;

        globalConfig.getPackageConfig().setSourceDir(packageRule.getSourceDir()).setBasePackage(packageRule.getBasePackage())
                .setControllerPackage(controllerRule.getPackageName())
                .setServicePackage(serviceRule.getPackageName()).setServiceImplPackage(serviceRule.getPackageName() + StrPool.DOT + serviceImplRule.getPackageName())
                .setMapperPackage(mapperRule.getPackageName()).setMapperXmlPath(xmlRule.getPath())
                .setEntityPackage(entityRule.getPackageName()).setDtoPackage(dtoRule.getPackageName())
                .setQueryPackage(queryRule.getPackageName()).setVoPackage(voRule.getPackageName())
        ;

        globalConfig.getStrategyConfig()
                .setTablePrefix(strategyRule.getTablePrefix())
                .setGenerateTables(tableNames);

        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        globalConfig.enableEntity().setClassPrefix(entityRule.getClassPrefix()).setClassSuffix(entityRule.getClassSuffix());
        globalConfig.enableVo().setClassPrefix(voRule.getClassPrefix()).setClassSuffix(voRule.getClassSuffix());
        globalConfig.enableQuery().setClassPrefix(queryRule.getClassPrefix()).setClassSuffix(queryRule.getClassSuffix());
        globalConfig.enableDto().setClassPrefix(dtoRule.getClassPrefix()).setClassSuffix(dtoRule.getClassSuffix());

        ControllerConfig controllerConfig = globalConfig.enableController();
        CONVERTER.convert(controllerRule, controllerConfig);

        MapperConfig mapperConfig = globalConfig.enableMapper();
        CONVERTER.convert(mapperRule, mapperConfig);

        ServiceConfig serviceConfig = globalConfig.enableService();
        CONVERTER.convert(serviceRule, serviceConfig);

        ServiceImplConfig serviceImplConfig = globalConfig.enableServiceImpl();
        CONVERTER.convert(serviceImplRule, serviceImplConfig);

        Generator generator = new Generator(dataSource, globalConfig);
        return generator.getTables();
    }


    @NotNull
    @SneakyThrows
    private GlobalConfig buildGlobalConfig(CodeCreator codeCreator) {
        CodeCreatorProperties.StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();
        CodeCreatorProperties.EntityRule entityRule = codeCreatorProperties.getEntityRule();
        CodeCreatorProperties.VoRule voRule = codeCreatorProperties.getVoRule();
        CodeCreatorProperties.QueryRule queryRule = codeCreatorProperties.getQueryRule();
        CodeCreatorProperties.DtoRule dtoRule = codeCreatorProperties.getDtoRule();

        PackageDesign packageDesign = codeCreator.getPackageDesign();
        ControllerDesign controllerDesign = codeCreator.getControllerDesign();
        ServiceDesign serviceDesign = codeCreator.getServiceDesign();
        ServiceImplDesign serviceImplDesign = codeCreator.getServiceImplDesign();
        MapperDesign mapperDesign = codeCreator.getMapperDesign();
        EntityDesign entityDesign = codeCreator.getEntityDesign();
        VoDesign voDesign = codeCreator.getVoDesign();
        DtoDesign dtoDesign = codeCreator.getDtoDesign();
        QueryDesign queryDesign = codeCreator.getQueryDesign();
        XmlDesign xmlDesign = codeCreator.getXmlDesign();

        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setCustomConfig(GLOBAL_CONFIG_KEY, codeCreator);
        globalConfig.setJdkVersion(17);

        globalConfig.getJavadocConfig()
                .setTableCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setTableSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setAuthor(packageDesign.getAuthor())
        ;

        String basePackage = StrUtil.isNotEmpty(packageDesign.getModule()) ? packageDesign.getBasePackage() + StrPool.DOT + packageDesign.getModule() : packageDesign.getBasePackage();
        String xmlPath = StrUtil.isNotEmpty(packageDesign.getModule()) ? xmlDesign.getPath() + StrPool.SLASH + packageDesign.getModule() : xmlDesign.getPath();
        globalConfig.getPackageConfig().
                setSourceDir(packageDesign.getSourceDir() + StrPool.SLASH + StrPool.SRC_MAIN_JAVA)
                .setBasePackage(basePackage)
                .setVoPackage(basePackage + StrPool.DOT + voDesign.getPackageName())
                .setDtoPackage(basePackage + StrPool.DOT + dtoDesign.getPackageName())
                .setQueryPackage(basePackage + StrPool.DOT + queryDesign.getPackageName())
                .setEntityPackage(basePackage + StrPool.DOT + entityDesign.getPackageName())
                .setMapperPackage(basePackage + StrPool.DOT + mapperDesign.getPackageName())
                .setMapperXmlPath(xmlPath)
                .setServicePackage(basePackage + StrPool.DOT + serviceDesign.getPackageName())
                .setServiceImplPackage(basePackage + StrPool.DOT + serviceDesign.getPackageName() + StrPool.DOT + serviceImplDesign.getPackageName())
                .setControllerPackage(basePackage + StrPool.DOT + controllerDesign.getPackageName());

        StrategyConfig strategyConfig = globalConfig.getStrategyConfig();
        BeanUtil.copyProperties(strategyRule, strategyConfig);

        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        EntityConfig entityConfig = globalConfig.enableEntity();
        CONVERTER.convert(entityDesign, entityConfig);
        entityConfig.setSuperClass(ClassUtils.forName(entityDesign.getSuperClassName()))
                .setGenericityType(entityRule.getGenericityType())
                .setImplInterfaces(entityRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC)
                .setWithActiveRecord(false);

        VoConfig voConfig = globalConfig.enableVo();
        CONVERTER.convert(voDesign, voConfig);
        voConfig.setSuperClass(ClassUtils.forName(voDesign.getSuperClassName()))
                .setGenericityType(voRule.getGenericityType())
                .setImplInterfaces(voRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        DtoConfig dtoConfig = globalConfig.enableDto();
        CONVERTER.convert(dtoDesign, dtoConfig);
        dtoConfig.setSuperClass(ClassUtils.forName(dtoDesign.getSuperClassName()))
                .setGenericityType(dtoRule.getGenericityType())
                .setImplInterfaces(dtoRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        QueryConfig queryConfig = globalConfig.enableQuery();
        CONVERTER.convert(queryDesign, queryConfig);
        queryConfig.setSuperClass(ClassUtils.forName(queryDesign.getSuperClassName()))
                .setGenericityType(queryRule.getGenericityType())
                .setImplInterfaces(queryRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        MapperConfig mapperConfig = globalConfig.enableMapper();
        CONVERTER.convert(mapperDesign, mapperConfig);
        mapperConfig.setSuperClass(ClassUtils.forName(mapperDesign.getSuperClassName()));

        globalConfig.enableMapperXml().setFilePrefix(xmlDesign.getFilePrefix())
                .setFileSuffix(xmlDesign.getFileSuffix());

        ServiceConfig serviceConfig = globalConfig.enableService();
        CONVERTER.convert(serviceDesign, serviceConfig);
        serviceConfig.setSuperClass(ClassUtils.forName(serviceDesign.getSuperClassName()));

        ServiceImplConfig serviceImplConfig = globalConfig.enableServiceImpl();
        CONVERTER.convert(serviceImplDesign, serviceImplConfig);
        serviceImplConfig.setSuperClass(ClassUtils.forName(serviceImplDesign.getSuperClassName()));

        ControllerConfig controllerConfig = globalConfig.enableController();
        CONVERTER.convert(controllerDesign, controllerConfig);
        controllerConfig.setSuperClass(ClassUtils.forName(controllerDesign.getSuperClassName()));

        return globalConfig;
    }

    public List<Table> getTableByCodeCreatorList(List<CodeCreator> codeCreatorList, Multimap<Long, CodeCreatorColumn> map) {

        List<Table> tables = new ArrayList<>();
        for (CodeCreator codeCreator : codeCreatorList) {
            GlobalConfig globalConfig = buildGlobalConfig(codeCreator);

            List<PropertyDesign> propertyDesignList = codeCreator.getPropertyDesign();
            Map<String, PropertyDesign> propertyDesignMap = CollUtil.isNotEmpty(propertyDesignList) ? CollHelper.uniqueIndex(propertyDesignList, PropertyDesign::getName, item -> item) : Collections.emptyMap();

            Table table = new Table();
            table.setName(codeCreator.getTableName());
            table.setComment(codeCreator.getTableDescription());
            table.setGlobalConfig(globalConfig);
            table.setEntityConfig(globalConfig.getEntityConfig());
            table.setTableConfig(globalConfig.getTableConfig(codeCreator.getTableName()));

            Collection<CodeCreatorColumn> columnList = map.get(codeCreator.getId());
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
}
