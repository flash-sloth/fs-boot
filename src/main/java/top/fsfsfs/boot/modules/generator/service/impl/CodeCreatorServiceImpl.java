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
import top.fsfsfs.boot.modules.generator.entity.type.PackageConfig;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.boot.modules.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorService;
import top.fsfsfs.boot.modules.generator.vo.TableImportDto;

import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
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
    private final CodeCreatorColumnMapper codeCreatorColumnMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(TableImportDto importDto) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/flash_sloth?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        GeneratorUtil generatorUtil = new GeneratorUtil(dataSource);
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
        PackageConfig packageConfig = new PackageConfig();
        codeCreator.setPackageConfig(packageConfig);
        return codeCreator;
    }
}
