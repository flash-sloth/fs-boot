package top.fsfsfs.main.generator.service.impl.inner;

import cn.hutool.core.util.StrUtil;
import com.baidu.fsg.uid.UidGenerator;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.constant.GenTypeEnum;
import top.fsfsfs.codegen.entity.Table;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.main.generator.entity.CodeCreator;
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
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.main.generator.vo.PreviewVo;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码树构建器
 * @author tangyh
 * @since 2024/6/28 14:40
 */
@RequiredArgsConstructor
public class CodeTreeBuilder {
    private final CodeCreatorProperties codeCreatorProperties;
    private final UidGenerator uidGenerator;
    private final Table table;
    private final Map<GenTypeEnum, String> codeMap;
    private final int tableIndex;

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
        buildLayer(previews, cache, backPackageDir, 1, controllerConfig.getPackageName(), table.buildControllerClassName(), codeMap.get(GenTypeEnum.CONTROLLER));
        PreviewVo serviceDir = buildLayer(previews, cache, backPackageDir, 2, serviceConfig.getPackageName(), table.buildServiceClassName(), codeMap.get(GenTypeEnum.SERVICE));
        buildLayer(previews, cache, serviceDir, -1, serviceImplConfig.getPackageName(), table.buildServiceImplClassName(), codeMap.get(GenTypeEnum.SERVICE_IMPL));
        buildLayer(previews, cache, backPackageDir, 3, mapperConfig.getPackageName(), table.buildMapperClassName(), codeMap.get(GenTypeEnum.MAPPER));

        // pojo层
        PreviewVo entityDir = buildLayer(previews, cache, backPackageDir, 4, entityConfig.getPackageName(), table.buildEntityClassName(), codeMap.get(GenTypeEnum.ENTITY));
        CodeCreatorProperties.EntityRule entityRule = codeCreatorProperties.getEntityRule();
        if (entityRule.getWithBaseClassEnabled()) {
            buildLayer(previews, cache, entityDir, -1, "base", table.buildEntityBaseClassName(), codeMap.get(GenTypeEnum.ENTITY_BASE));
        }
        buildLayer(previews, cache, backPackageDir, 5, voConfig.getPackageName(), table.buildVoClassName(), codeMap.get(GenTypeEnum.VO));
        buildLayer(previews, cache, backPackageDir, 6, dtoConfig.getPackageName(), table.buildDtoClassName(), codeMap.get(GenTypeEnum.DTO));
        buildLayer(previews, cache, backPackageDir, 7, queryConfig.getPackageName(), table.buildQueryClassName(), codeMap.get(GenTypeEnum.QUERY));

        // xml层
        buildXml(previews, cache, resourceDir, xmlConfig, table.buildMapperXmlFileName(), codeMap.get(GenTypeEnum.MAPPER_XML));
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
                    .setId(uidGenerator.getUid())
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
                    .setId(uidGenerator.getUid())
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
                    .setId(uidGenerator.getUid())
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
                    .setId(uidGenerator.getUid())
                    .setName("fs-boot")
                    .setParentId(null);
            cache.put(packageConfig.getSourceDir(), root);
            previews.add(root);
        }
        return root;
    }

    private void buildXml(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo resourceDir,
                          XmlDesign xmlConfig, String name, String content) {
        String cacheKey = resourceDir.getPath() + File.separator + xmlConfig.getPath();
        PreviewVo xmlDir = cache.get(cacheKey);
        if (xmlDir == null) {
            xmlDir = new PreviewVo();
            xmlDir.setPath(resourceDir.getPath() + File.separator + xmlConfig.getPath())
                    .setType("dir")
                    .setIsReadonly(true)
                    .setId(uidGenerator.getUid())
                    .setWeight(tableIndex)
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
                .setWeight(tableIndex)
                .setName(name + ".xml")
                .setParentId(xmlDir.getId());
        previews.add(xmlFile);
    }

    private PreviewVo buildLayer(List<PreviewVo> previews, Map<String, PreviewVo> cache, PreviewVo parent,
                                 int dirIndex, String packageName, String name, String content) {
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
                .setWeight(tableIndex)
                .setName(name + ".java")
                .setParentId(layerDir.getId());
        previews.add(codeFile);
        return layerDir;
    }

}
