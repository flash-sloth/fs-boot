package top.fsfsfs.boot.modules.generator.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.fsg.uid.UidGenerator;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.modules.generator.GeneratorUtil;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.entity.CodeCreatorColumn;
import top.fsfsfs.boot.modules.generator.entity.type.DtoConfig;
import top.fsfsfs.boot.modules.generator.entity.type.EntityConfig;
import top.fsfsfs.boot.modules.generator.entity.type.PackageConfig;
import top.fsfsfs.boot.modules.generator.entity.type.QueryConfig;
import top.fsfsfs.boot.modules.generator.entity.type.VoConfig;
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

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 代码生成 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@Service
@RequiredArgsConstructor
@Slf4j
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

        List<CodeCreator> list = new ArrayList<>();
        List<CodeCreatorColumn> columnList = new ArrayList<>();
        for (Table table : tables) {
            CodeCreator codeCreator = buildCodeCreator(importDto, table);
            list.add(codeCreator);

            List<Column> allColumns = table.getAllColumns();
            int weight = 0;
            for (Column column : allColumns) {
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
                codeCreatorColumn.setWeight(weight++);
                columnList.add(codeCreatorColumn);
            }
        }

        saveBatch(list);
        codeCreatorColumnMapper.insertBatch(columnList);
        return true;
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

        return codeCreator;
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
        Class<?>[] implInterfaces = entityRule.getImplInterfaces();
        if (implInterfaces != null) {
            String[] implInterfaceNames = Arrays.stream(implInterfaces).map(Class::getName).toArray(String[]::new);
            entityConfig.setImplInterfaceNames(implInterfaceNames);
        } else {
            entityConfig.setImplInterfaceNames(new String[0]);
        }
        codeCreator.setEntityConfig(entityConfig);
    }

    private void fillPackageConfig(Table table, CodeCreator codeCreator) {
        PackageConfig packageConfig = new PackageConfig();
        PackageRule packageRule = codeCreatorProperties.getPackageRule();
        packageConfig.setSourceDir(packageRule.getSourceDir()).setBasePackage(packageRule.getBasePackage()).setAuthor(packageRule.getAuthor());
        codeCreator.setPackageConfig(packageConfig);
    }
}
