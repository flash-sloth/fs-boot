package top.fsfsfs.main.generator.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.baidu.fsg.uid.UidGenerator;
import com.google.common.collect.Multimap;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.constant.GenTypeConst;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.mybatisflex.codegen.generator.IGenerator;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.main.generator.dto.TableImportDto;
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
import top.fsfsfs.main.generator.entity.type.front.FormDesign;
import top.fsfsfs.main.generator.entity.type.front.ListDesign;
import top.fsfsfs.main.generator.entity.type.front.PropertyDesign;
import top.fsfsfs.main.generator.entity.type.front.SearchDesign;
import top.fsfsfs.main.generator.mapper.CodeCreatorColumnMapper;
import top.fsfsfs.main.generator.mapper.CodeCreatorMapper;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.service.CodeCreatorService;
import top.fsfsfs.main.generator.service.impl.inner.GeneratorUtil;
import top.fsfsfs.main.generator.service.impl.inner.ImportTableBuilder;
import top.fsfsfs.main.generator.vo.PreviewVo;
import top.fsfsfs.util.utils.CollHelper;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.io.File;
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
            Collection<IGenerator> generators = GeneratorFactory.getGenerators();
            Map<String, String> codeMap = new HashMap(generators.size());
            for (IGenerator generator : generators) {
                codeMap.put(generator.getGenType(), generator.preview(table, table.getGlobalConfig()));
            }

            buildCodeTree(table, previews, cache, codeMap, i);
        }
        log.info("Code is generated successfully.");

        log.info("size ={} ", previews.size());
        List<Tree<Long>> treeList = FsTreeUtil.build(previews, new PreviewVo.PreviewNodeParser());
        log.info("treeList ={} ", treeList);
        return treeList;
    }


    public void buildCodeTree(Table table, List<PreviewVo> previews, Map<String, PreviewVo> cache, Map<String, String> codeMap, int tableIndex) {

        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(GeneratorUtil.GLOBAL_CONFIG_KEY);
        PackageDesign packageConfig = codeCreator.getPackageDesign();
        ControllerDesign controllerConfig = codeCreator.getControllerDesign();
        ServiceDesign serviceConfig = codeCreator.getServiceDesign();
        ServiceImplDesign serviceImplConfig = codeCreator.getServiceImplDesign();
        MapperDesign mapperConfig = codeCreator.getMapperDesign();
        EntityDesign entityConfig = codeCreator.getEntityDesign();
        VoDesign voConfig = codeCreator.getVoDesign();
        DtoDesign dtoConfig = codeCreator.getDtoDesign();
        QueryDesign queryConfig = codeCreator.getQueryDesign();
        XmlDesign xmlConfig = codeCreator.getXmlDesign();

        PreviewVo root = buildRoot(previews, cache, tableIndex, packageConfig);
        PreviewVo javaDir = buildSrcMainJava(previews, cache, tableIndex, root);
        PreviewVo resourceDir = buildSrcMainResource(previews, cache, root);

        PreviewVo backPackageDir = buildModule(previews, cache, tableIndex, packageConfig, javaDir);

        buildLayer(previews, cache, backPackageDir, tableIndex, 1, controllerConfig.getPackageName(), table.buildControllerClassName(), codeMap.get(GenTypeConst.CONTROLLER));
        PreviewVo serviceDir = buildLayer(previews, cache, backPackageDir, tableIndex, 2, serviceConfig.getPackageName(), table.buildServiceClassName(), codeMap.get(GenTypeConst.SERVICE));
//        buildLayer(previews, cache, backPackageDir, tableIndex, 3, serviceImplConfig.getPackageName(), table.buildServiceImplClassName(), codeMap.get(GenTypeConst.SERVICE_IMPL));
        buildLayer(previews, cache, serviceDir, tableIndex, 0, serviceImplConfig.getPackageName(), table.buildServiceImplClassName(), codeMap.get(GenTypeConst.SERVICE_IMPL));

        buildLayer(previews, cache, backPackageDir, tableIndex, 4, mapperConfig.getPackageName(), table.buildMapperClassName(), codeMap.get(GenTypeConst.MAPPER));
        PreviewVo entityDir = buildLayer(previews, cache, backPackageDir, tableIndex, 5, entityConfig.getPackageName(), table.buildEntityClassName(), codeMap.get(GenTypeConst.ENTITY));
        CodeCreatorProperties.EntityRule entityRule = codeCreatorProperties.getEntityRule();
        if (entityRule.getWithBaseClassEnabled()) {
            buildLayer(previews, cache, entityDir, tableIndex, 0, "base", table.buildEntityBaseClassName(), codeMap.get(GenTypeConst.ENTITY_BASE));
        }
        buildLayer(previews, cache, backPackageDir, tableIndex, 6, voConfig.getPackageName(), table.buildVoClassName(), codeMap.get(GenTypeConst.VO));
        buildLayer(previews, cache, backPackageDir, tableIndex, 7, dtoConfig.getPackageName(), table.buildDtoClassName(), codeMap.get(GenTypeConst.DTO));
        buildLayer(previews, cache, backPackageDir, tableIndex, 8, queryConfig.getPackageName(), table.buildQueryClassName(), codeMap.get(GenTypeConst.QUERY));
//        buildControllerLayer(previews, cache, codeMap, tableIndex, controllerConfig, backPackageDir);
//        buildServiceLayer(previews, cache, codeMap, tableIndex, serviceConfig, backPackageDir);
//        buildServiceImplLayer(previews, cache, codeMap, tableIndex, serviceImplConfig, backPackageDir);
//        buildMapperLayer(previews, cache, codeMap, tableIndex, mapperConfig, backPackageDir);
//        buildEntityLayer(previews, cache, codeMap, tableIndex, entityConfig, backPackageDir);
//        buildVoLayer(previews, cache, codeMap, tableIndex, voConfig, backPackageDir);
//        buildDtoLayer(previews, cache, codeMap, tableIndex, dtoConfig, backPackageDir);
//        buildQueryLayer(previews, cache, codeMap, tableIndex, queryConfig, backPackageDir);

        buildXml(previews, cache, resourceDir, tableIndex, xmlConfig, table.buildMapperXmlFileName(), codeMap.get(GenTypeConst.MAPPER_XML));
    }

    @NotNull
    private PreviewVo buildModule(List<PreviewVo> previews, Map<String, PreviewVo> cache, int tableIndex, PackageDesign packageConfig, PreviewVo javaDir) {
        String name = packageConfig.getBasePackage() + "." + packageConfig.getModule();
        String packageKey = javaDir.getPath() + File.separator + StrUtil.replace(name, ".", File.separator);
        PreviewVo backPackageDir = cache.get(packageKey);
        if (backPackageDir == null) {
            backPackageDir = new PreviewVo();
            backPackageDir.setPath(packageKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(name)
                    .setParentId(javaDir.getId());
            cache.put(packageKey, backPackageDir);
            previews.add(backPackageDir);
        }
        return backPackageDir;
    }

    @NotNull
    private PreviewVo buildSrcMainResource(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo root) {

        String resourceDirKey = root.getPath() + File.separator + StrPool.SRC_MAIN_RESOURCES;
        PreviewVo resourceDir = cache.get(resourceDirKey);
        if (resourceDir == null) {
            resourceDir = new PreviewVo();
            resourceDir.setPath(resourceDirKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(2)
                    .setName(StrPool.SRC_MAIN_RESOURCES)
                    .setParentId(root.getId());

            cache.put(resourceDirKey, resourceDir);
//            resourceDir.setWeight(resourceDir.getWeight() + 1);
            previews.add(resourceDir);
        }
        return resourceDir;
    }

    @NotNull
    private PreviewVo buildSrcMainJava(List<PreviewVo> previews, Map<String, PreviewVo> cache, int tableIndex, PreviewVo root) {
        String javaDirKey = root.getPath() + File.separator + StrUtil.replace(StrPool.SRC_MAIN_JAVA, "/", File.separator);

        PreviewVo javaDir = cache.get(javaDirKey);
        if (javaDir == null) {
            javaDir = new PreviewVo();
            javaDir.setPath(javaDirKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(StrPool.SRC_MAIN_JAVA)
                    .setParentId(root.getId());
            cache.put(javaDirKey, javaDir);
//            javaDir.setWeight(javaDir.getWeight() + 1);
            previews.add(javaDir);
        }
        return javaDir;
    }

    @NotNull
    private PreviewVo buildRoot(List<PreviewVo> previews, Map<String, PreviewVo> cache, int tableIndex, PackageDesign packageConfig) {
        PreviewVo root = cache.get(packageConfig.getSourceDir());
        if (root == null) {
            root = new PreviewVo();
            root.setPath(packageConfig.getSourceDir())
                    .setIsReadonly(true)
                    .setType("project")
                    .setWeight(tableIndex + 1)
                    .setId(uidGenerator.getUid())
                    .setName("fs-boot")
                    .setParentId(null);
            cache.put(packageConfig.getSourceDir(), root);
            previews.add(root);
        }
        return root;
    }

    private void buildXml(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo resourceDir,
                          int tableIndex, XmlDesign xmlConfig, String name, String content) {
        String cacheKey = resourceDir.getPath() + File.separator + xmlConfig.getPath();
        PreviewVo xmlDir = cache.get(cacheKey);
        if (xmlDir == null) {
            xmlDir = new PreviewVo();
            xmlDir.setPath(resourceDir.getPath() + File.separator + xmlConfig.getPath())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + 1)
                    .setName(xmlConfig.getPath())
                    .setParentId(resourceDir.getId());
            cache.put(cacheKey, xmlDir);
            previews.add(xmlDir);
        }

        PreviewVo xmlFile = new PreviewVo();
        xmlFile.setPath(xmlDir.getPath() + File.separator + name)
                .setType("file")
                .setIsReadonly(false)
                .setContent(content)
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(name + ".xml")
                .setParentId(xmlDir.getId());
        previews.add(xmlFile);
    }

    private PreviewVo buildLayer(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo parent,
                                 int tableIndex, int dirIndex, String packageName, String name, String content) {
        String path = parent.getPath() + File.separator + packageName;
        PreviewVo layerDir = cache.get(path);
        if (layerDir == null) {
            layerDir = new PreviewVo();
            layerDir.setPath(path)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex + dirIndex)
                    .setName(packageName)
                    .setParentId(parent.getId());
            cache.put(path, layerDir);
            previews.add(layerDir);
        }

        PreviewVo codeFile = new PreviewVo();
        codeFile.setPath(layerDir.getPath() + File.separator + name + ".java")
                .setType("file")
                .setIsReadonly(false)
                .setContent(content)
                .setId(uidGenerator.getUid())
                .setWeight(tableIndex + 1)
                .setName(name + ".java")
                .setParentId(layerDir.getId());
        previews.add(codeFile);
        return layerDir;
    }

    public List<Table> getTables(List<Long> ids) {

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
}
