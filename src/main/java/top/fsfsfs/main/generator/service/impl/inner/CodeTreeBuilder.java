package top.fsfsfs.main.generator.service.impl.inner;

import cn.hutool.core.lang.Snowflake;
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
import top.fsfsfs.main.generator.entity.type.PackageDesign;
import top.fsfsfs.main.generator.entity.type.front.FrontDesign;
import top.fsfsfs.main.generator.vo.PreviewVo;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * 代码树构建器
 * @author tangyh
 * @since 2024/6/28 14:40
 */
@Slf4j
public class CodeTreeBuilder {
    private final List<CodeCreatorContent> backendCodeCreatorContentList;
    private final List<CodeCreatorContent> frontCodeCreatorContentList;
    private final Table table;
    private final int tableIndex;

    public CodeTreeBuilder(Table table, List<CodeCreatorContent> codeCreatorContentList, int tableIndex) {
        this.table = table;
        this.tableIndex = tableIndex;
        this.backendCodeCreatorContentList = codeCreatorContentList.stream().filter(item -> GenTypeEnum.BACKEND_LIST.contains(item.getGenType())).toList();
        this.frontCodeCreatorContentList = codeCreatorContentList.stream().filter(item -> GenTypeEnum.FRONT_LIST.contains(item.getGenType())).toList();
    }

    private final static Snowflake SNOWFLAKE = IdUtil.getSnowflake(1, 1);

    public void buildCodeTree(List<PreviewVo> previews, Map<String, PreviewVo> cache) {
        buildBackendCodeTree(previews, cache);
        buildFrontCodeTree(previews, cache);
    }

    private void buildBackendCodeTree(List<PreviewVo> treeDataList, Map<String, PreviewVo> cacheMap) {
        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);
        PackageDesign packageConfig = codeCreator.getPackageDesign();

        // 根目录
        PreviewVo root = buildBackendRoot(treeDataList, cacheMap, packageConfig);

        for (int j = 0; j < backendCodeCreatorContentList.size(); j++) {
            CodeCreatorContent creatorContent = backendCodeCreatorContentList.get(j);
            PreviewVo parent = root;
            List<String> pathList = StrUtil.split(creatorContent.getPath(), StrPool.SLASH);

            for (int i = 0; i < pathList.size(); i++) {
                String path = pathList.get(i);
                String key = parent.getPath() + StrPool.SLASH + path;

                PreviewVo layerDir;
                if (cacheMap.containsKey(key)) {
                    layerDir = cacheMap.get(key);
                } else {
                    layerDir = new PreviewVo();
                    layerDir.setName(path).setWeight(tableIndex + j);

                    if (i + 1 == pathList.size()) {
                        layerDir.setType("file").setIsReadonly(false).setContent(creatorContent.getContent()).setId(creatorContent.getId());
                    } else {
                        layerDir.setType("dir").setIsReadonly(true).setId(SNOWFLAKE.nextId());
                    }
                    layerDir.setPath(key);
                    layerDir.setParentId(parent.getId());

                    cacheMap.put(key, layerDir);
                    treeDataList.add(layerDir);
                }
                parent = layerDir;
            }
        }
    }

    private void buildFrontCodeTree(List<PreviewVo> treeDataList, Map<String, PreviewVo> cacheMap) {
        GlobalConfig globalConfig = table.getGlobalConfig();
        Map<String, Object> customConfig = globalConfig.getCustomConfig();
        CodeCreator codeCreator = (CodeCreator) customConfig.get(TableBuilder.GLOBAL_CONFIG_KEY);
        FrontDesign frontDesign = codeCreator.getFrontDesign();

        PreviewVo root = buildFrontRoot(treeDataList, cacheMap, frontDesign);
        for (int j = 0; j < frontCodeCreatorContentList.size(); j++) {
            CodeCreatorContent creatorContent = frontCodeCreatorContentList.get(j);

            PreviewVo parent = root;
            List<String> pathList = StrUtil.split(creatorContent.getPath(), StrPool.SLASH);

            for (int i = 0; i < pathList.size(); i++) {
                String path = pathList.get(i);
                String key = parent.getPath() + StrPool.SLASH + path;

                PreviewVo layerDir;
                if (cacheMap.containsKey(key)) {
                    layerDir = cacheMap.get(key);
                } else {
                    layerDir = new PreviewVo();
                    layerDir.setName(path).setWeight(tableIndex + j);

                    if (i + 1 == pathList.size()) {
                        layerDir.setType("file").setIsReadonly(false).setContent(creatorContent.getContent()).setId(creatorContent.getId());
                    } else {
                        layerDir.setType("dir").setIsReadonly(true).setId(SNOWFLAKE.nextId());
                    }
                    layerDir.setPath(key);
                    layerDir.setParentId(parent.getId());

                    cacheMap.put(key, layerDir);
                    treeDataList.add(layerDir);
                }
                parent = layerDir;
            }
        }
    }

    @NotNull
    private PreviewVo buildBackendRoot(List<PreviewVo> previews, Map<String, PreviewVo> cache, PackageDesign packageConfig) {
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

    @NotNull
    private PreviewVo buildFrontRoot(List<PreviewVo> previews, Map<String, PreviewVo> cache, FrontDesign frontDesign) {
        PreviewVo root = cache.get(frontDesign.getSourceDir());
        if (root == null) {
            root = new PreviewVo();
            root.setPath(frontDesign.getSourceDir())
                    .setIsReadonly(true)
                    .setType("project")
                    .setWeight(tableIndex + 1)
                    .setId(SNOWFLAKE.nextId())
                    .setName("fs-web")
                    .setParentId(null);
            cache.put(frontDesign.getSourceDir(), root);
            previews.add(root);
        }
        return root;
    }
}
