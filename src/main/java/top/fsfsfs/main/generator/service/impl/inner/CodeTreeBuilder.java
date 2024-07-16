package top.fsfsfs.main.generator.service.impl.inner;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.entity.Table;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.entity.CodeCreatorContent;
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
import top.fsfsfs.main.generator.entity.type.front.FrontDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.vo.PreviewVo;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码树构建器
 * @author tangyh
 * @since 2024/6/28 14:40
 */
@Slf4j
public class CodeTreeBuilder {
    private final CodeCreatorProperties codeCreatorProperties;
    private final List<CodeCreatorContent> backendCodeCreatorContentList;
    private final List<CodeCreatorContent> frontCodeCreatorContentList;
    private final Table table;
    private final Map<GenTypeEnum, CodeCreatorContent> codeMap;
    private final int tableIndex;

    public CodeTreeBuilder(CodeCreatorProperties codeCreatorProperties, List<CodeCreatorContent> codeCreatorContentList,
                           Table table, Map<GenTypeEnum, CodeCreatorContent> codeMap, int tableIndex) {
        this.codeCreatorProperties = codeCreatorProperties;
        this.table = table;
        this.codeMap = codeMap;
        this.tableIndex = tableIndex;
        this.backendCodeCreatorContentList = codeCreatorContentList.stream().filter(item -> GenTypeEnum.BACKEND_LIST.contains(item.getGenType())).toList();
        this.frontCodeCreatorContentList = codeCreatorContentList.stream().filter(item -> GenTypeEnum.FRONT_LIST.contains(item.getGenType())).toList();
    }

    private final static Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    public void buildCodeTree(List<PreviewVo> previews, Map<String, PreviewVo> cache) {
        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);
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

        // 根目录
        PreviewVo root = buildRoot(previews, cache, packageConfig);
        // src/main/java
        PreviewVo javaDir = buildSrcMainJava(previews, cache, root);
        // src/main/resources
        PreviewVo resourceDir = buildSrcMainResource(previews, cache, root);
        // 基础包 + 模块
        PreviewVo backPackageDir = buildModule(previews, cache, packageConfig, javaDir);

        // mvc 层
        buildLayer(previews, cache, backPackageDir, 1, controllerConfig.getPackageName(), table.buildControllerClassName(), GenTypeEnum.CONTROLLER);
        PreviewVo serviceDir = buildLayer(previews, cache, backPackageDir, 2, serviceConfig.getPackageName(), table.buildServiceClassName(), GenTypeEnum.SERVICE);
        buildLayer(previews, cache, serviceDir, -1, serviceImplConfig.getPackageName(), table.buildServiceImplClassName(), GenTypeEnum.SERVICE_IMPL);
        buildLayer(previews, cache, backPackageDir, 3, mapperConfig.getPackageName(), table.buildMapperClassName(), GenTypeEnum.MAPPER);

        // pojo层
        PreviewVo entityDir = buildLayer(previews, cache, backPackageDir, 4, entityConfig.getPackageName(), table.buildEntityClassName(), GenTypeEnum.ENTITY);
        CodeCreatorProperties.EntityRule entityRule = codeCreatorProperties.getEntityRule();
        if (entityRule.getWithBaseClassEnabled()) {
            buildLayer(previews, cache, entityDir, -1, "base", table.buildEntityBaseClassName(), GenTypeEnum.ENTITY_BASE);
        }
        buildLayer(previews, cache, backPackageDir, 5, voConfig.getPackageName(), table.buildVoClassName(), GenTypeEnum.VO);
        buildLayer(previews, cache, backPackageDir, 6, dtoConfig.getPackageName(), table.buildDtoClassName(), GenTypeEnum.DTO);
        buildLayer(previews, cache, backPackageDir, 7, queryConfig.getPackageName(), table.buildQueryClassName(), GenTypeEnum.QUERY);

        // xml层
        buildXml(previews, cache, resourceDir, packageConfig, xmlConfig, table.buildMapperXmlFileName(), GenTypeEnum.MAPPER_XML);

        buildFrontCodeTree(previews, cache);
    }

    private void buildFrontCodeTree(List<PreviewVo> treeDataList, Map<String, PreviewVo> cacheMap) {
        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);
        FrontDesign frontDesign = codeCreator.getFrontDesign();


        PreviewVo parent = buildFrontRoot(treeDataList, cacheMap, frontDesign);
        treeDataList.add(parent);

        for (CodeCreatorContent creatorContent : frontCodeCreatorContentList) {

            List<String> pathList = StrUtil.split(creatorContent.getPath(), StrPool.SLASH);

            for (int i = 0; i < pathList.size(); i++) {
                String path = pathList.get(i);
                String key = parent.getPath() + File.separator + path;

                PreviewVo layerDir;
                if (cacheMap.containsKey(key)) {
                    layerDir = cacheMap.get(key);
                } else {
                    layerDir = new PreviewVo();
                    layerDir.setName(path);
//                        .setWeight(tableIndex + dirIndex)

                    if (i + 1 == pathList.size()) {
                        layerDir.setType("file").setIsReadonly(false).setContent(creatorContent.getContent()).setId(creatorContent.getId());

                    } else {
                        layerDir.setType("dir").setIsReadonly(true).setId(SNOWFLAKE.nextId());
                    }
                    layerDir.setPath(key);
                    layerDir.setParentId(parent.getId());
                }
                parent = layerDir;

                cacheMap.put(key, layerDir);
                treeDataList.add(layerDir);
            }
        }
    }

    @NotNull
    private PreviewVo buildFrontRoot(List<PreviewVo> previews, Map<String, PreviewVo> cache, FrontDesign frontDesign) {
        PreviewVo root = cache.get(frontDesign.getSourceDir());
        if (root == null) {
            root = new PreviewVo();
            root.setPath(frontDesign.getSourceDir())
                    .setIsReadonly(true)
                    .setType("project")
                    .setWeight(tableIndex)
                    .setId(SNOWFLAKE.nextId())
                    .setName("fs-web")
                    .setParentId(null);
            cache.put(frontDesign.getSourceDir(), root);
            previews.add(root);
        }
        return root;
    }

    @NotNull
    private PreviewVo buildModule(List<PreviewVo> previews, Map<String, PreviewVo> cache, PackageDesign packageConfig, PreviewVo javaDir) {
        String name = StrUtil.isEmpty(packageConfig.getModule()) ? packageConfig.getBasePackage() : packageConfig.getBasePackage() + StrPool.DOT + packageConfig.getModule();
        String packageKey = javaDir.getPath() + File.separator + StrUtil.replace(name, StrPool.DOT, File.separator);
        PreviewVo backPackageDir = cache.get(packageKey);
        if (backPackageDir == null) {
            backPackageDir = new PreviewVo();
            backPackageDir.setPath(packageKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(SNOWFLAKE.nextId())
                    .setWeight(tableIndex)
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
                    .setId(SNOWFLAKE.nextId())
                    .setWeight(tableIndex + 2)
                    .setName(StrPool.SRC_MAIN_RESOURCES)
                    .setParentId(root.getId());

            cache.put(resourceDirKey, resourceDir);
            previews.add(resourceDir);
        }
        return resourceDir;
    }

    @NotNull
    private PreviewVo buildSrcMainJava(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo root) {
        String javaDirKey = root.getPath() + File.separator + StrUtil.replace(StrPool.SRC_MAIN_JAVA, "/", File.separator);

        PreviewVo javaDir = cache.get(javaDirKey);
        if (javaDir == null) {
            javaDir = new PreviewVo();
            javaDir.setPath(javaDirKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(SNOWFLAKE.nextId())
                    .setWeight(tableIndex + 1)
                    .setName(StrPool.SRC_MAIN_JAVA)
                    .setParentId(root.getId());
            cache.put(javaDirKey, javaDir);
            previews.add(javaDir);
        }
        return javaDir;
    }

    @NotNull
    private PreviewVo buildRoot(List<PreviewVo> previews, Map<String, PreviewVo> cache, PackageDesign packageConfig) {
        PreviewVo root = cache.get(packageConfig.getSourceDir());
        if (root == null) {
            root = new PreviewVo();
            root.setPath(packageConfig.getSourceDir())
                    .setIsReadonly(true)
                    .setType("project")
                    .setWeight(tableIndex)
                    .setId(SNOWFLAKE.nextId())
                    .setName("fs-boot")
                    .setParentId(null);
            cache.put(packageConfig.getSourceDir(), root);
            previews.add(root);
        }
        return root;
    }

    private void buildXml(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo resourceDir,
                          PackageDesign packageConfig, XmlDesign xmlConfig, String name, GenTypeEnum genType) {

        String cacheKey = resourceDir.getPath() + File.separator + xmlConfig.getPath();
        PreviewVo xmlDir = cache.get(cacheKey);

        if (xmlDir == null) {
            xmlDir = new PreviewVo();
            xmlDir.setPath(cacheKey)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(SNOWFLAKE.nextId())
                    .setWeight(tableIndex)
                    .setName(xmlConfig.getPath())
                    .setParentId(resourceDir.getId());
            cache.put(cacheKey, xmlDir);
            previews.add(xmlDir);
        }

        PreviewVo parent = xmlDir;

        if (StrUtil.isNotEmpty(packageConfig.getModule())) {
            String moduleCacheKey = xmlDir.getPath() + File.separator + packageConfig.getModule();
            PreviewVo xmlModuleDir = cache.get(moduleCacheKey);
            if (xmlModuleDir == null) {
                xmlModuleDir = new PreviewVo();
                xmlModuleDir.setPath(moduleCacheKey)
                        .setType("dir")
                        .setIsReadonly(true)
                        .setId(SNOWFLAKE.nextId())
                        .setWeight(tableIndex)
                        .setName(packageConfig.getModule())
                        .setParentId(xmlDir.getId());
                cache.put(moduleCacheKey, xmlModuleDir);
                previews.add(xmlModuleDir);
            }
            parent = xmlModuleDir;
        }
        CodeCreatorContent codeCreatorContent = codeMap.get(genType);
        if (codeCreatorContent != null) {
            PreviewVo xmlFile = new PreviewVo();
            xmlFile.setPath(parent.getPath() + File.separator + name)
                    .setType("file")
                    .setIsReadonly(false)
                    .setContent(codeCreatorContent.getContent())
                    .setId(codeCreatorContent.getId())
                    .setWeight(tableIndex)
                    .setName(name + ".xml")
                    .setParentId(parent.getId());
            previews.add(xmlFile);
        }
    }

    private PreviewVo buildLayer(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo parent,
                                 int dirIndex, String packageName, String name, GenTypeEnum genType) {
        String path = parent.getPath() + File.separator + packageName;
        PreviewVo layerDir = cache.get(path);
        if (layerDir == null) {
            layerDir = new PreviewVo();
            layerDir.setPath(path)
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(SNOWFLAKE.nextId())
                    .setWeight(tableIndex + dirIndex)
                    .setName(packageName)
                    .setParentId(parent.getId());
            cache.put(path, layerDir);
            previews.add(layerDir);
        }

        CodeCreatorContent codeCreatorContent = codeMap.get(genType);
        if (codeCreatorContent != null) {

            PreviewVo codeFile = new PreviewVo();
            codeFile.setPath(layerDir.getPath() + File.separator + name + ".java")
                    .setType("file")
                    .setIsReadonly(false)
                    .setContent(codeCreatorContent.getContent())
                    .setId(codeCreatorContent.getId())
                    .setWeight(tableIndex)
                    .setName(name + ".java")
                    .setParentId(layerDir.getId());
            previews.add(codeFile);
        }

        return layerDir;
    }


    public static void main(String[] args) {
        List<CodeCreatorContent> list = new ArrayList<>();

        CodeCreatorContent codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(1L);
        codeCreatorContent.setContent("ttt");
        codeCreatorContent.setPath("src/views/main/abcde/codeTestSimple/index.tsx");
        list.add(codeCreatorContent);

        codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(2L);
        codeCreatorContent.setContent("2222");
        codeCreatorContent.setPath("src/views/main/abcde/codeTestSimple/modules/form.vue");
        list.add(codeCreatorContent);


        codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(3L);
        codeCreatorContent.setContent("3333");
        codeCreatorContent.setPath("src/views/main/abcde/codeTestSimple/modules/wrapper.vue");
        list.add(codeCreatorContent);


        codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(4L);
        codeCreatorContent.setContent("4444");
        codeCreatorContent.setPath("src/views/main/abcde/codeTestSimple/data/form.tsx");
        list.add(codeCreatorContent);

        codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(5L);
        codeCreatorContent.setContent("555");
        codeCreatorContent.setPath("src/service/main/abcde/codeTestSimple/api.ts");
        list.add(codeCreatorContent);

        codeCreatorContent = new CodeCreatorContent();
        codeCreatorContent.setId(5L);
        codeCreatorContent.setContent("555");
        codeCreatorContent.setPath("src/service/main/abcde/codeTestSimple/model.d.ts");
        list.add(codeCreatorContent);


        List<PreviewVo> treeDataList = new ArrayList<>();
        Map<String, PreviewVo> cacheMap = new HashMap<>();

        PreviewVo parent = new PreviewVo();
        parent.setPath("/Users/tangyh/fs-web").setId(null);
        treeDataList.add(parent);

        for (CodeCreatorContent creatorContent : list) {

            List<String> pathList = StrUtil.split(creatorContent.getPath(), StrPool.SLASH);

            for (int i = 0; i < pathList.size(); i++) {
                String path = pathList.get(i);
                String key = parent.getPath() + File.separator + path;

                PreviewVo layerDir;
                if (cacheMap.containsKey(key)) {
                    layerDir = cacheMap.get(key);
                } else {
                    layerDir = new PreviewVo();
                    layerDir.setName(path);
//                        .setWeight(tableIndex + dirIndex)

                    if (i + 1 == pathList.size()) {
                        layerDir.setType("file").setIsReadonly(false).setContent(creatorContent.getContent()).setId(codeCreatorContent.getId());

                    } else {
                        layerDir.setType("dir").setIsReadonly(true).setId(SNOWFLAKE.nextId());
                    }
                    layerDir.setPath(key);
                    layerDir.setParentId(parent.getId());
                }
                parent = layerDir;

                cacheMap.put(key, layerDir);
                treeDataList.add(layerDir);
            }

        }

//        log.info(JSONUtil.toJsonStr(treeDataList));

        List<Tree<Long>> treeList = FsTreeUtil.build(treeDataList, new PreviewVo.PreviewNodeParser());
        log.info("treeList ={} ", treeList);

    }
}
