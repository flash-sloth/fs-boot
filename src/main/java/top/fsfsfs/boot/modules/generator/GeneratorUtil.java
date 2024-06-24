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

package top.fsfsfs.boot.modules.generator;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Multimap;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ControllerConfig;
import com.mybatisflex.codegen.config.DtoConfig;
import com.mybatisflex.codegen.config.EntityConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.MapperConfig;
import com.mybatisflex.codegen.config.QueryConfig;
import com.mybatisflex.codegen.config.ServiceConfig;
import com.mybatisflex.codegen.config.ServiceImplConfig;
import com.mybatisflex.codegen.config.StrategyConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.codegen.config.VoConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.dialect.TsTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.entity.CodeCreatorColumn;
import top.fsfsfs.boot.modules.generator.entity.type.ControllerDesign;
import top.fsfsfs.boot.modules.generator.entity.type.DtoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.EntityDesign;
import top.fsfsfs.boot.modules.generator.entity.type.MapperDesign;
import top.fsfsfs.boot.modules.generator.entity.type.PackageDesign;
import top.fsfsfs.boot.modules.generator.entity.type.QueryDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.boot.modules.generator.entity.type.VoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.XmlDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.boot.modules.generator.mapping.ControllerDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.DtoDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.EntityDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.MapperDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.QueryDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.ServiceDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.ServiceImplDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.VoDesignMapping;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.DtoRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.EntityRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.QueryRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.StrategyRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.VoRule;
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
public class GeneratorUtil {
    private DataSource dataSource;
    private CodeCreatorProperties codeCreatorProperties;
    public final static String GLOBAL_CONFIG_KEY = "$codeCreator";

    public GeneratorUtil(CodeCreatorProperties codeCreatorProperties) {
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
        ControllerDesignMapping.INSTANCE.copySourceProperties(controllerRule, controllerConfig);

        MapperConfig mapperConfig = globalConfig.enableMapper();
        MapperDesignMapping.INSTANCE.copySourceProperties(mapperRule, mapperConfig);

        ServiceConfig serviceConfig = globalConfig.enableService();
        ServiceDesignMapping.INSTANCE.copySourceProperties(serviceRule, serviceConfig);

        ServiceImplConfig serviceImplConfig = globalConfig.enableServiceImpl();
        ServiceImplDesignMapping.INSTANCE.copySourceProperties(serviceImplRule, serviceImplConfig);

        Generator generator = new Generator(dataSource, globalConfig);
        return generator.getTables();
    }


    @NotNull
    @SneakyThrows
    public GlobalConfig buildGlobalConfig(CodeCreator codeCreator) {
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

        String basePackage = packageDesign.getBasePackage() + StrPool.DOT + packageDesign.getModule();
        globalConfig.getPackageConfig().
                setSourceDir(packageDesign.getSourceDir() + StrPool.SLASH + StrPool.SRC_MAIN_JAVA)
                .setBasePackage(basePackage)
                .setVoPackage(basePackage + StrPool.DOT + voDesign.getPackageName())
                .setDtoPackage(basePackage + StrPool.DOT + dtoDesign.getPackageName())
                .setQueryPackage(basePackage + StrPool.DOT + queryDesign.getPackageName())
                .setEntityPackage(basePackage + StrPool.DOT + entityDesign.getPackageName())
                .setMapperPackage(basePackage + StrPool.DOT + mapperDesign.getPackageName())
                .setMapperXmlPath(packageDesign.getSourceDir() + StrPool.SLASH + StrPool.SRC_MAIN_RESOURCES + StrPool.SLASH + xmlDesign.getPath())
                .setServicePackage(basePackage + StrPool.DOT + serviceDesign.getPackageName())
                .setServiceImplPackage(basePackage + StrPool.DOT + serviceDesign.getPackageName() + StrPool.DOT + serviceImplDesign.getPackageName())
                .setControllerPackage(basePackage + StrPool.DOT + controllerDesign.getPackageName());

        StrategyConfig strategyConfig = globalConfig.getStrategyConfig();
        BeanUtil.copyProperties(strategyRule, strategyConfig);

        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        EntityConfig entityConfig = globalConfig.enableEntity();
        EntityDesignMapping.INSTANCE.copySourceProperties(entityDesign, entityConfig);
        entityConfig.setSuperClass(ClassUtils.forName(entityDesign.getSuperClassName()))
                .setGenericityType(entityRule.getGenericityType())
                .setImplInterfaces(entityRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC)
                .setWithActiveRecord(false);

        VoConfig voConfig = globalConfig.enableVo();
        VoDesignMapping.INSTANCE.copySourceProperties(voDesign, voConfig);
        voConfig.setSuperClass(ClassUtils.forName(voDesign.getSuperClassName()))
                .setGenericityType(voRule.getGenericityType())
                .setImplInterfaces(voRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        DtoConfig dtoConfig = globalConfig.enableDto();
        DtoDesignMapping.INSTANCE.copySourceProperties(dtoDesign, dtoConfig);
        dtoConfig.setSuperClass(ClassUtils.forName(dtoDesign.getSuperClassName()))
                .setGenericityType(dtoRule.getGenericityType())
                .setImplInterfaces(dtoRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        QueryConfig queryConfig = globalConfig.enableQuery();
        QueryDesignMapping.INSTANCE.copySourceProperties(queryDesign, queryConfig);
        queryConfig.setSuperClass(ClassUtils.forName(queryDesign.getSuperClassName()))
                .setGenericityType(queryRule.getGenericityType())
                .setImplInterfaces(queryRule.getImplInterfaces())
                .setSwaggerVersion(EntityConfig.SwaggerVersion.DOC);

        MapperConfig mapperConfig = globalConfig.enableMapper();
        MapperDesignMapping.INSTANCE.copySourceProperties(mapperDesign, mapperConfig);
        mapperConfig.setSuperClass(ClassUtils.forName(mapperDesign.getSuperClassName()));

        globalConfig.enableMapperXml().setFilePrefix(xmlDesign.getFilePrefix())
                .setFileSuffix(xmlDesign.getFileSuffix());

        ServiceConfig serviceConfig = globalConfig.enableService();
        ServiceDesignMapping.INSTANCE.copySourceProperties(serviceDesign, serviceConfig);
        serviceConfig.setSuperClass(ClassUtils.forName(serviceDesign.getSuperClassName()));

        ServiceImplConfig serviceImplConfig = globalConfig.enableServiceImpl();
        ServiceImplDesignMapping.INSTANCE.copySourceProperties(serviceImplDesign, serviceImplConfig);
        serviceImplConfig.setSuperClass(ClassUtils.forName(serviceImplDesign.getSuperClassName()));

        ControllerConfig controllerConfig = globalConfig.enableController();
        ControllerDesignMapping.INSTANCE.copySourceProperties(controllerDesign, controllerConfig);
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
            table.setGlobalConfig(globalConfig);
            table.setEntityConfig(globalConfig.getEntityConfig());
            table.setName(codeCreator.getTableName());
            table.setComment(codeCreator.getTableDescription());

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
