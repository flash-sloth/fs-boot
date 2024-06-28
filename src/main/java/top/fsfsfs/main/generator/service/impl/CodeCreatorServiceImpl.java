package top.fsfsfs.main.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.fsg.uid.UidGenerator;
import com.google.common.collect.Multimap;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.constant.GenTypeEnum;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.mybatisflex.codegen.generator.IGenerator;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.main.generator.dto.CodeGenDto;
import top.fsfsfs.main.generator.dto.TableImportDto;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.entity.CodeCreatorColumn;
import top.fsfsfs.main.generator.entity.CodeCreatorContent;
import top.fsfsfs.main.generator.entity.type.front.FormDesign;
import top.fsfsfs.main.generator.entity.type.front.ListDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.entity.type.front.SearchDesign;
import top.fsfsfs.main.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.main.generator.mapper.CodeCreatorContentMapper;
import top.fsfsfs.main.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.service.CodeCreatorService;
import top.fsfsfs.main.generator.service.impl.inner.CodeTreeBuilder;
import top.fsfsfs.main.generator.service.impl.inner.GeneratorUtil;
import top.fsfsfs.main.generator.service.impl.inner.ImportTableBuilder;
import top.fsfsfs.main.generator.vo.PreviewVo;
import top.fsfsfs.util.utils.CollHelper;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private final CodeCreatorContentMapper codeCreatorContentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(TableImportDto importDto) {
        //TODO 查询数据库
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/flash_sloth?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        GeneratorUtil generatorUtil = new GeneratorUtil(dataSource, codeCreatorProperties);
        List<Table> tables = generatorUtil.whenImportGetTable(importDto.getTableNames());

        List<CodeCreator> list = new ArrayList<>();
        List<CodeCreatorColumn> columnList = new ArrayList<>();
        for (Table table : tables) {
            long id = uidGenerator.getUid();
            ImportTableBuilder importTableBuilder = new ImportTableBuilder(table, codeCreatorProperties, id);
            CodeCreator codeCreator = importTableBuilder.buildCodeCreator(importDto.getDsId());

            List<PropertyDesign> propertyDesignList = new ArrayList<>();
            List<SearchDesign> searchDesignList = new ArrayList<>();
            List<FormDesign> formDesignList = new ArrayList<>();
            List<ListDesign> listDesignList = new ArrayList<>();

            List<Column> allColumns = table.getAllColumns();
            for (int i = 0; i < allColumns.size(); i++) {
                Column column = allColumns.get(i);

                columnList.add(importTableBuilder.fillCodeCreatorColumn(column, i));

                propertyDesignList.add(importTableBuilder.fillPropertyDesign(column));
                searchDesignList.add(importTableBuilder.fillSearchDesign(column, i));
                listDesignList.add(importTableBuilder.fillListDesign(column, i));
                formDesignList.add(importTableBuilder.fillFormDesign(column, i));
                //TODO 其他 tree
            }


            codeCreator.setPropertyDesign(propertyDesignList);
            codeCreator.setSearchDesign(searchDesignList);
            codeCreator.setFromDesign(formDesignList);
            codeCreator.setListDesign(listDesignList);
            list.add(codeCreator);
        }

        saveBatch(list);
        codeCreatorColumnMapper.insertBatch(columnList);
        return true;
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

        Map<String, PreviewVo> cache = new LinkedHashMap<>();
        List<PreviewVo> previews = new ArrayList<>();

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            GlobalConfig globalConfig = table.getGlobalConfig();
            Map<String, Object> customConfig = globalConfig.getCustomConfig();
            CodeCreator codeCreator = (CodeCreator) customConfig.get(GeneratorUtil.GLOBAL_CONFIG_KEY);
            List<CodeCreatorContent> codeCreatorContentList = codeCreatorContentMapper.selectListByQuery(QueryWrapper.create().eq(CodeCreatorContent::getCodeCreatorId, codeCreator.getId()));

            if (CollUtil.isEmpty(codeCreatorContentList)) {
                Collection<IGenerator> generators = GeneratorFactory.getGenerators();
                for (IGenerator generator : generators) {
                    String previewCode = generator.preview(table, globalConfig);

                    CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
                    codeCreatorContent.setGenType(generator.getGenType());
                    codeCreatorContent.setContent(previewCode);
                    codeCreatorContent.setCodeCreatorId(codeCreator.getId());

                    codeCreatorContentList.add(codeCreatorContent);
                }

                codeCreatorContentMapper.insertBatch(codeCreatorContentList);
            }

            Map<GenTypeEnum, String> codeMap = new HashMap<>(codeCreatorContentList.size());
            for (CodeCreatorContent codeCreatorContent : codeCreatorContentList) {
                codeMap.put(codeCreatorContent.getGenType(), codeCreatorContent.getContent());
            }
            CodeTreeBuilder codeTreeBuilder = new CodeTreeBuilder(codeCreatorProperties, uidGenerator, table, codeMap, i);
            codeTreeBuilder.buildCodeTree(previews, cache);
        }
        log.info("Code is generated successfully. size ={}", previews.size());

        List<Tree<Long>> treeList = FsTreeUtil.build(previews, new PreviewVo.PreviewNodeParser());
        log.info("treeList ={} ", treeList);
        return treeList;
    }

    private List<Table> getTables(List<Long> ids) {

        List<CodeCreator> codeCreatorList = listByIds(ids);
        if (CollUtil.isEmpty(codeCreatorList)) {
            throw BizException.wrap("请选择需要预览的表");
        }
        List<Long> idList = codeCreatorList.stream().map(CodeCreator::getId).toList();

        QueryWrapper wrapper = QueryWrapper.create().in(CodeCreatorColumn::getCodeCreatorId, idList).orderBy(CodeCreatorColumn::getWeight, true);
        List<CodeCreatorColumn> allColumnList = codeCreatorColumnMapper.selectListByQuery(wrapper);

        Multimap<Long, CodeCreatorColumn> map = CollHelper.iterableToMultiMap(allColumnList, CodeCreatorColumn::getCodeCreatorId, item -> item);

        return new GeneratorUtil(codeCreatorProperties).getTableByCodeCreatorList(codeCreatorList, map);
    }

    @Override
    public void generator(CodeGenDto genDto) {
        List<Table> tables = getTables(genDto.getIds());
        if (tables == null || tables.isEmpty()) {
            log.error("table not found.");
            return;
        } else {
            log.info("find tables: {}", tables.stream().map(Table::getName).collect(Collectors.toSet()));
        }

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);
            GlobalConfig globalConfig = table.getGlobalConfig();
            Map<String, Object> customConfig = globalConfig.getCustomConfig();
            CodeCreator codeCreator = (CodeCreator) customConfig.get(GeneratorUtil.GLOBAL_CONFIG_KEY);
            List<CodeCreatorContent> codeCreatorContentList = codeCreatorContentMapper.selectListByQuery(QueryWrapper.create().eq(CodeCreatorContent::getCodeCreatorId, codeCreator.getId()));

            if (CollUtil.isEmpty(codeCreatorContentList)) {
                Collection<IGenerator> generators = GeneratorFactory.getGenerators();
                for (IGenerator generator : generators) {
                    String previewCode = generator.preview(table, globalConfig);

                    CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
                    codeCreatorContent.setGenType(generator.getGenType());
                    codeCreatorContent.setContent(previewCode);
                    codeCreatorContent.setCodeCreatorId(codeCreator.getId());

                    codeCreatorContentList.add(codeCreatorContent);
                }

                codeCreatorContentMapper.insertBatch(codeCreatorContentList);
            }

            for (CodeCreatorContent codeCreatorContent : codeCreatorContentList) {
                IGenerator generator = GeneratorFactory.getGenerator(codeCreatorContent.getGenType());
                generator.setTemplateContent(codeCreatorContent.getContent());
                generator.generate(table, table.getGlobalConfig());
            }
        }
        log.info("Code is generated successfully.");
    }
}
