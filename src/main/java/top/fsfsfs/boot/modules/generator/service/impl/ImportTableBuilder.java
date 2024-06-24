package top.fsfsfs.boot.modules.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.entity.CodeCreatorColumn;
import top.fsfsfs.boot.modules.generator.entity.type.ControllerDesign;
import top.fsfsfs.boot.modules.generator.entity.type.DtoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.EntityDesign;
import top.fsfsfs.boot.modules.generator.entity.type.MapperDesign;
import top.fsfsfs.boot.modules.generator.entity.type.MenuDesign;
import top.fsfsfs.boot.modules.generator.entity.type.PackageDesign;
import top.fsfsfs.boot.modules.generator.entity.type.QueryDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceDesign;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.boot.modules.generator.entity.type.VoDesign;
import top.fsfsfs.boot.modules.generator.entity.type.XmlDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.ButtonDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.FormDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.ListDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.boot.modules.generator.entity.type.front.SearchDesign;
import top.fsfsfs.boot.modules.generator.mapping.DtoDesignMapping;
import top.fsfsfs.boot.modules.generator.mapping.VoDesignMapping;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/6/23 19:13
 */
@RequiredArgsConstructor
public class ImportTableBuilder {
    private final Table table;
    private final CodeCreatorProperties codeCreatorProperties;
    private final Long id;

    @NotNull
    public CodeCreator buildCodeCreator(Long dsId) {
        CodeCreator codeCreator = new CodeCreator();
        codeCreator.setId(id);
        codeCreator.setDsId(dsId);
        codeCreator.setTableName(table.getName());
        codeCreator.setTableDescription(table.getComment());
        fillPackageConfig(table, codeCreator);
        fillEntityConfig(table, codeCreator);
        fillVoConfig(codeCreator);
        fillDtoConfig(codeCreator);
        fillQueryConfig(table, codeCreator);
        fillMapperConfig(table, codeCreator);
        fillXmlConfig(table, codeCreator);
        fillServiceConfig(table, codeCreator);
        fillServiceImplConfig(table, codeCreator);
        fillControllerConfig(table, codeCreator);
        fillMenuDesign(codeCreator);
        fillButtonDesign(codeCreator);
        return codeCreator;
    }

    private void fillMenuDesign(CodeCreator codeCreator) {
        codeCreator.setMenuDesign(MenuDesign.builder()
                .execute(true)
                .name(table.getSwaggerComment())
                .build());
    }

    private static void fillButtonDesign(CodeCreator codeCreator) {
        List<ButtonDesign> buttonDesign = new ArrayList<>();
        buttonDesign.add(ButtonDesign.builder().state(true).code("view").name("查看").build());
        buttonDesign.add(ButtonDesign.builder().state(true).code("add").name("新增").build());
        buttonDesign.add(ButtonDesign.builder().state(true).code("edit").name("编辑").build());
        buttonDesign.add(ButtonDesign.builder().state(true).code("delete").name("删除").build());
        buttonDesign.add(ButtonDesign.builder().state(false).code("import").name("导入").build());
        buttonDesign.add(ButtonDesign.builder().state(false).code("export").name("导出").build());
        codeCreator.setButtonDesign(buttonDesign);
    }


    private void fillControllerConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ControllerRule controllerRule = codeCreatorProperties.getControllerRule();
        ControllerDesign controllerConfig = new ControllerDesign();
        controllerConfig.setPackageName(controllerRule.getPackageName())
                .setClassPrefix(controllerRule.getClassPrefix()).setClassSuffix(controllerRule.getClassSuffix())
                .setSuperClassName(controllerRule.getSuperClass() != null ? controllerRule.getSuperClass().getName() : "")
                .setRequestMappingPrefix(controllerRule.getRequestMappingPrefix())
                .setWithCrud(controllerRule.getWithCrud())
                .setRestStyle(controllerRule.getRestStyle())
        ;
        codeCreator.setControllerDesign(controllerConfig);
    }

    private void fillServiceImplConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ServiceImplRule serviceRule = codeCreatorProperties.getServiceImplRule();
        ServiceImplDesign serviceConfig = new ServiceImplDesign();
        serviceConfig.setPackageName(serviceRule.getPackageName())
                .setClassPrefix(serviceRule.getClassPrefix()).setClassSuffix(serviceRule.getClassSuffix())
                .setSuperClassName(serviceRule.getSuperClass() != null ? serviceRule.getSuperClass().getName() : "")
        ;
        codeCreator.setServiceImplDesign(serviceConfig);
    }

    private void fillServiceConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.ServiceRule serviceRule = codeCreatorProperties.getServiceRule();
        ServiceDesign serviceConfig = new ServiceDesign();
        serviceConfig.setPackageName(serviceRule.getPackageName())
                .setClassPrefix(serviceRule.getClassPrefix()).setClassSuffix(serviceRule.getClassSuffix())
                .setSuperClassName(serviceRule.getSuperClass() != null ? serviceRule.getSuperClass().getName() : "")
        ;
        codeCreator.setServiceDesign(serviceConfig);
    }

    private void fillXmlConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.XmlRule xmlRule = codeCreatorProperties.getXmlRule();
        XmlDesign xmlConfig = new XmlDesign();
        xmlConfig.setPath(xmlRule.getPath()).setFilePrefix(xmlRule.getFilePrefix()).setFileSuffix(xmlRule.getFileSuffix());
        codeCreator.setXmlDesign(xmlConfig);
    }

    private void fillMapperConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.MapperRule mapperRule = codeCreatorProperties.getMapperRule();
        MapperDesign mapperConfig = new MapperDesign();
        mapperConfig.setPackageName(mapperRule.getPackageName())
                .setClassPrefix(mapperRule.getClassPrefix()).setClassSuffix(mapperRule.getClassSuffix())
                .setSuperClassName(mapperRule.getSuperClass() != null ? mapperRule.getSuperClass().getName() : "")
        ;
        codeCreator.setMapperDesign(mapperConfig);
    }

    private void fillQueryConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.QueryRule queryRule = codeCreatorProperties.getQueryRule();
        QueryDesign queryConfig = new QueryDesign();
        queryConfig.setPackageName(queryRule.getPackageName())
                .setClassPrefix(queryRule.getClassPrefix()).setClassSuffix(queryRule.getClassSuffix())
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
        codeCreator.setQueryDesign(queryConfig);
    }

    private void fillDtoConfig(CodeCreator codeCreator) {
        CodeCreatorProperties.DtoRule dtoRule = codeCreatorProperties.getDtoRule();
        DtoDesign dtoConfig = new DtoDesign();
        DtoDesignMapping.INSTANCE.copySourceProperties(dtoRule, dtoConfig);
        dtoConfig.setSuperClassName(dtoRule.getSuperClass() != null ? dtoRule.getSuperClass().getName() : "")
                .setGenericityTypeName(dtoRule.getGenericityType() != null ? dtoRule.getGenericityType().getName() : "")
        ;
        Class<?>[] implInterfaces = dtoRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            dtoConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            dtoConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setDtoDesign(dtoConfig);
    }

    private void fillVoConfig(CodeCreator codeCreator) {
        CodeCreatorProperties.VoRule voRule = codeCreatorProperties.getVoRule();
        VoDesign voConfig = new VoDesign();
        VoDesignMapping.INSTANCE.copySourceProperties(voRule, voConfig);
        voConfig.setSuperClassName(voRule.getSuperClass() != null ? voRule.getSuperClass().getName() : "")
                .setGenericityTypeName(voRule.getGenericityType() != null ? voRule.getGenericityType().getName() : "");
        Class<?>[] implInterfaces = voRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            voConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            voConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setVoDesign(voConfig);
    }

    private void fillEntityConfig(Table table, CodeCreator codeCreator) {
        CodeCreatorProperties.EntityRule entityRule = codeCreatorProperties.getEntityRule();
        EntityDesign entityConfig = new EntityDesign();
        entityConfig
                .setPackageName(entityRule.getPackageName())
                .setName(table.buildEntityClassName())
                .setDescription(table.getSwaggerComment())
                .setSuperClassName(entityRule.getSuperClass() != null ? entityRule.getSuperClass().getName() : "")
                .setGenericityTypeName(entityRule.getGenericityType() != null ? entityRule.getGenericityType().getName() : "")
                .setWithLombok(entityRule.getWithLombok())
                .setWithChain(entityRule.getWithChain())
                .setWithBaseClassEnable(entityRule.getWithBaseClassEnabled())
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
        codeCreator.setEntityDesign(entityConfig);
    }

    private void fillPackageConfig(Table table, CodeCreator codeCreator) {
        PackageDesign packageConfig = new PackageDesign();
        CodeCreatorProperties.PackageRule packageRule = codeCreatorProperties.getPackageRule();
        packageConfig.setSourceDir(packageRule.getSourceDir()).setBasePackage(packageRule.getBasePackage()).setAuthor(packageRule.getAuthor());
        codeCreator.setPackageDesign(packageConfig);
    }


    public CodeCreatorColumn fillCodeCreatorColumn(Column column, int i) {
        CodeCreatorColumn codeCreatorColumn = new CodeCreatorColumn();
        codeCreatorColumn.setCodeCreatorId(id);
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
        return codeCreatorColumn;
    }

    public PropertyDesign fillPropertyDesign(Column column) {
        CodeCreatorProperties.StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();
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
        return propertyDesign;

    }

    public SearchDesign fillSearchDesign(Column column, int index) {
        SearchDesign design = new SearchDesign();
        design.setName(column.getName());
        design.setShow(true);
        design.setHidden(false);
        design.setSequence(index);
        return design;
    }

    public ListDesign fillListDesign(Column column, int index) {
        ListDesign design = new ListDesign();
        design.setName(column.getName());
        design.setShow(true);
        design.setHidden(false);
        design.setComponentType(column.getTsType());
        design.setSequence(index);
        return design;
    }

    public FormDesign fillFormDesign(Column column, int index) {
        FormDesign design = new FormDesign();
        design.setName(column.getName());
        design.setShow(true);
        design.setHidden(false);
        design.setComponentType(column.getTsType());
        design.setSequence(index);
        return design;
    }

}
