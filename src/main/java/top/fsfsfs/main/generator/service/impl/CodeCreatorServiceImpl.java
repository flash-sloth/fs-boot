package top.fsfsfs.main.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baidu.fsg.uid.UidGenerator;
import com.google.common.collect.Multimap;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.request.DownloadVO;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.constant.GenerationStrategyEnum;
import top.fsfsfs.codegen.entity.Column;
import top.fsfsfs.codegen.entity.Table;
import top.fsfsfs.codegen.generator.GeneratorFactory;
import top.fsfsfs.codegen.generator.IGenerator;
import top.fsfsfs.main.base.service.BaseDatasourceService;
import top.fsfsfs.main.generator.dto.CodeGenDto;
import top.fsfsfs.main.generator.dto.CodePreviewDto;
import top.fsfsfs.main.generator.dto.TableImportDto;
import top.fsfsfs.main.generator.entity.CodeBaseClass;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.entity.CodeCreatorColumn;
import top.fsfsfs.main.generator.entity.CodeCreatorContent;
import top.fsfsfs.main.generator.entity.CodeType;
import top.fsfsfs.main.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.main.generator.mapper.CodeCreatorContentMapper;
import top.fsfsfs.main.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.service.CodeBaseClassService;
import top.fsfsfs.main.generator.service.CodeCreatorService;
import top.fsfsfs.main.generator.service.CodeTypeService;
import top.fsfsfs.main.generator.service.impl.inner.CodeTreeBuilder;
import top.fsfsfs.main.generator.service.impl.inner.ImportTableBuilder;
import top.fsfsfs.main.generator.service.impl.inner.TableBuilder;
import top.fsfsfs.main.generator.vo.CodeCreatorVo;
import top.fsfsfs.main.generator.vo.PreviewVo;
import top.fsfsfs.util.utils.CollHelper;
import top.fsfsfs.util.utils.FsMetaUtil;
import top.fsfsfs.util.utils.FsTreeUtil;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    private final BaseDatasourceService baseDatasourceService;
    private final CodeBaseClassService codeBaseClassService;
    private final CodeTypeService codeTypeService;

    @Override
    public List<CodeCreatorVo> listTableMetadata(Long dsId) {
        DataSource ds = baseDatasourceService.getDs(dsId);
        List<cn.hutool.db.meta.Table> tables = FsMetaUtil.getTables(ds);
        return tables.stream().map(table -> CodeCreatorVo.builder().tableName(table.getTableName()).tableDescription(table.getComment()).build()).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean importTable(TableImportDto importDto) {
        DataSource dataSource = baseDatasourceService.getDs(importDto.getDsId());
        List<CodeBaseClass> codeBaseClassList = codeBaseClassService.list(QueryWrapper.create().eq(CodeBaseClass::getState, true).orderBy(CodeBaseClass::getWeight, true));
        List<CodeType> codeTypeList = codeTypeService.listAll();
        TableBuilder generatorUtil = new TableBuilder(dataSource, codeTypeList, codeCreatorProperties);
        List<Table> tables = generatorUtil.whenImportGetTable(importDto.getTableNames());

        List<CodeCreator> list = new ArrayList<>();
        List<CodeCreatorColumn> columnList = new ArrayList<>();
        for (Table table : tables) {
            long id = uidGenerator.getUid();

            ImportTableBuilder importTableBuilder = new ImportTableBuilder(table, codeCreatorProperties, codeBaseClassList, id);
            CodeCreator codeCreator = importTableBuilder.buildCodeCreator(importDto.getDsId());

            List<Column> allColumns = table.getAllColumns();
            for (int i = 0; i < allColumns.size(); i++) {
                Column column = allColumns.get(i);

                columnList.add(importTableBuilder.fillCodeCreatorColumn(column, i));
            }
            list.add(codeCreator);
        }

        saveBatch(list);
        codeCreatorColumnMapper.insertBatch(columnList);
        return true;
    }


    @Override
    public List<Tree<Long>> preview(CodePreviewDto previewDto) {

        List<Table> tables = getTables(previewDto.getIds());
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
            CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);

            QueryWrapper wrapper = QueryWrapper.create().eq(CodeCreatorContent::getCodeCreatorId, codeCreator.getId());
            if (previewDto.getReload() != null && previewDto.getReload()) {
                codeCreatorContentMapper.deleteByQuery(wrapper);
            }

            List<CodeCreatorContent> codeCreatorContentList = codeCreatorContentMapper.selectListByQuery(wrapper);

            if (CollUtil.isEmpty(codeCreatorContentList)) {
                Collection<IGenerator> generators = GeneratorFactory.getGenerators();
                for (IGenerator generator : generators) {
                    String previewCode = generator.preview(table, globalConfig);

                    CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
                    codeCreatorContent.setGenType(generator.getGenType());
                    codeCreatorContent.setContent(previewCode);
                    codeCreatorContent.setPath(generator.getFilePath(table, globalConfig, false));
                    codeCreatorContent.setCodeCreatorId(codeCreator.getId());

                    codeCreatorContentList.add(codeCreatorContent);
                }

                codeCreatorContentMapper.insertBatch(codeCreatorContentList);
            }

            Map<GenTypeEnum, CodeCreatorContent> codeMap = new HashMap<>(codeCreatorContentList.size());
            for (CodeCreatorContent codeCreatorContent : codeCreatorContentList) {
                codeMap.put(codeCreatorContent.getGenType(), codeCreatorContent);
            }
            CodeTreeBuilder codeTreeBuilder = new CodeTreeBuilder(codeCreatorProperties, table, codeMap, i);
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

        List<CodeType> codeTypeList = codeTypeService.listAll();

        return new TableBuilder(codeTypeList, codeCreatorProperties).getTableByCodeCreatorList(codeCreatorList, map);
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
            CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);
            Set<Long> codeIds = genDto.getGenStrategy().keySet();
            QueryWrapper wrapper = QueryWrapper.create().eq(CodeCreatorContent::getCodeCreatorId, codeCreator.getId())
                    .in(CodeCreatorContent::getId, codeIds, CollUtil.isNotEmpty(codeIds));

            List<CodeCreatorContent> codeCreatorContentList = codeCreatorContentMapper.selectListByQuery(wrapper);
            if (CollUtil.isEmpty(codeCreatorContentList)) {
                Collection<IGenerator> generators = GeneratorFactory.getGenerators();
                for (IGenerator generator : generators) {
                    String previewCode = generator.preview(table, globalConfig);

                    CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
                    codeCreatorContent.setGenType(generator.getGenType());
                    codeCreatorContent.setContent(previewCode);
                    codeCreatorContent.setPath(generator.getFilePath(table, globalConfig, false));
                    codeCreatorContent.setCodeCreatorId(codeCreator.getId());

                    codeCreatorContentList.add(codeCreatorContent);
                }

                codeCreatorContentMapper.insertBatch(codeCreatorContentList);
            }

            for (CodeCreatorContent codeCreatorContent : codeCreatorContentList) {
                IGenerator generator = GeneratorFactory.getGenerator(codeCreatorContent.getGenType());
                GenerationStrategyEnum generationStrategy = genDto.getGenStrategy().getOrDefault(codeCreatorContent.getId(), GenerationStrategyEnum.OVERWRITE);
                globalConfig.enableController().setGenerationStrategy(generationStrategy);
                globalConfig.enableService().setGenerationStrategy(generationStrategy);
                globalConfig.enableServiceImpl().setGenerationStrategy(generationStrategy);
                globalConfig.enableMapper().setGenerationStrategy(generationStrategy);
                globalConfig.enableMapperXml().setGenerationStrategy(generationStrategy);
                globalConfig.enableDto().setGenerationStrategy(generationStrategy);
                globalConfig.enableEntity().setGenerationStrategy(generationStrategy);
                globalConfig.enableVo().setGenerationStrategy(generationStrategy);
                globalConfig.enableQuery().setGenerationStrategy(generationStrategy);
                generator.generate(table, globalConfig, codeCreatorContent.getContent());
            }
        }
        log.info("生成完成");
    }

    @SneakyThrows
    @Override
    public DownloadVO download(List<Long> ids, List<Long> codeIds) {
        List<Table> tables = getTables(ids);
        if (tables == null || tables.isEmpty()) {
            log.error("table not found.");
            throw BizException.wrap("请选择您要下载的表");
        } else {
            log.info("find tables: {}", tables.stream().map(Table::getName).collect(Collectors.toSet()));
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        StringBuilder name = new StringBuilder();

        for (int i = 0; i < tables.size(); i++) {
            Table table = tables.get(i);

            name.append(table.buildEntityClassName());
            if (i != tables.size() - 1) {
                name.append("|");
            }

            GlobalConfig globalConfig = table.getGlobalConfig();
            Map<String, Object> customConfig = globalConfig.getCustomConfig();
            CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);

            QueryWrapper wrapper = QueryWrapper.create().eq(CodeCreatorContent::getCodeCreatorId, codeCreator.getId())
                    .in(CodeCreatorContent::getId, codeIds, CollUtil.isNotEmpty(codeIds));
            List<CodeCreatorContent> codeCreatorContentList = codeCreatorContentMapper.selectListByQuery(wrapper);

            if (CollUtil.isEmpty(codeCreatorContentList)) {
                Collection<IGenerator> generators = GeneratorFactory.getGenerators();
                for (IGenerator generator : generators) {
                    String previewCode = generator.preview(table, globalConfig);

                    CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
                    codeCreatorContent.setGenType(generator.getGenType());
                    codeCreatorContent.setPath(generator.getFilePath(table, globalConfig, false));
                    codeCreatorContent.setContent(previewCode);
                    codeCreatorContent.setCodeCreatorId(codeCreator.getId());

                    codeCreatorContentList.add(codeCreatorContent);
                }

                codeCreatorContentMapper.insertBatch(codeCreatorContentList);
            }

            for (CodeCreatorContent codeCreatorContent : codeCreatorContentList) {
                String content = codeCreatorContent.getContent();
                String path = codeCreatorContent.getPath();
                zip.putNextEntry(new ZipEntry(path));
                IOUtils.write(content, zip, StrPool.UTF8);
                zip.closeEntry();
                IoUtil.flush(zip);
            }
        }

        IoUtil.close(zip);

        String zipName = "代码(" + name + ").zip";
        return DownloadVO.builder().data(outputStream.toByteArray()).fileName(zipName).build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeAllByIds(List<Long> ids) {
        boolean b = removeByIds(ids);
        codeCreatorColumnMapper.deleteByQuery(QueryWrapper.create().in(CodeCreatorColumn::getCodeCreatorId, ids));
        return b;
    }
}
