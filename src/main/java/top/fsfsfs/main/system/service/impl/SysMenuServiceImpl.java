package top.fsfsfs.main.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.common.enumeration.system.ResourceTypeEnum;
import top.fsfsfs.main.auth.vo.RouterMeta;
import top.fsfsfs.main.system.dto.SysMenuDto;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.mapper.SysMenuMapper;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuResultVo;
import top.fsfsfs.util.utils.ArgumentAssert;
import top.fsfsfs.util.utils.BeanPlusUtil;
import top.fsfsfs.util.utils.FsTreeUtil;
import top.fsfsfs.util.utils.JsonUtil;
import top.fsfsfs.util.utils.ValidatorUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-15
 */
@Service
@Slf4j
public class SysMenuServiceImpl extends SuperServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    /**
     * 1. 管理员拥有全部菜单；普通用户根据角色进行查询；
     * 2. 构造树结构
     * 3. 构造vueRouter数据
     * @return
     */
    @Override
    public List<Tree<Long>> listVisibleRouter() {
        // 1
        List<SysMenu> menuList = list();
        List<SysMenuResultVo> resultList = BeanUtil.copyToList(menuList, SysMenuResultVo.class);

//        2
        List<Tree<Long>> menuTreeList = FsTreeUtil.build(resultList, new RouterNodeParser());

//        3
        forEachTree(menuTreeList, 1, null);


        return menuTreeList;
    }

    public static class RouterNodeParser implements NodeParser<SysMenuResultVo, Long> {
        @Override
        public void parse(SysMenuResultVo treeNode, Tree<Long> tree) {
            Map<String, Object> map = BeanUtil.beanToMap(treeNode);
            tree.putAll(map);

            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
        }
    }

    private static final String META = "meta";
    private static final String META_JSON = "metaJson";

    private void forEachTree(List<Tree<Long>> tree, int level, Tree<Long> parent) {

        for (Tree<Long> item : tree) {
            RouterMeta meta = null;

            if (item.get(META) == null) {
                if (ObjectUtil.isNotEmpty(item.get(META_JSON)) && !StrPool.BRACE.equals(item.get(META_JSON))) {
                    meta = JsonUtil.parse((String) item.get(META_JSON), RouterMeta.class);
                }
                if (meta == null) {
                    meta = new RouterMeta();
                }

                meta.put("component", item.get("component"));
                if (ObjectUtil.isEmpty(meta.get("title"))) {
                    meta.put("title", item.getName());
                }
                meta.put("icon", item.get("icon"));

                // 视图需要隐藏
                meta.put("hideInMenu", item.get("hideInMenu") != null ? item.get("hideInMenu") : false);

                if ((Boolean) meta.get("hideInMenu") && ObjectUtil.isEmpty(meta.get("activeMenu")) && parent != null) {
                    meta.put("activeMenu", parent.getName());
                }

//                TODO 这段逻辑建议在配置菜单时，一次性配置完成
//                内链
                if ("02".equals(item.get("openWith"))) {
                    //  是否内嵌页面
                    meta.put("frameSrc", item.get("component"));
                    item.put("component", "IFRAME");
                    meta.put("component", "_builtin/iframe/index");
                } else if ("03".equals(item.get("openWith"))) {
                    // 是否外链
                    item.put("component", "IFRAME");
                    meta.put("href", item.get("path"));
                    item.put("path", "/IFRAME");
                }

            } else {
                meta = (RouterMeta) item.get(META);
            }

            //            soybean-admin 要求的格式
            if (parent != null) {
                item.setName(parent.getName() + "_" + item.getName());
            }

            item.put(META, meta);

            if (CollUtil.isNotEmpty(item.getChildren())) {
                forEachTree(item.getChildren(), level + 1, item);
            }
        }
    }

    @Override
    public Boolean check(Long id, String code) {
        return count(QueryWrapper.create().ne(SysMenu::getId, id, id != null).eq(SysMenu::getCode, code)) > 0;
    }

    @Override
    public Boolean checkPath(Long id, Long applicationId, String path) {
        return count(QueryWrapper.create().ne(SysMenu::getId, id, id != null).eq(SysMenu::getSubSystemId, applicationId).eq(SysMenu::getPath, path)) > 0;
    }

    @Override
    public Boolean checkName(Long id, Long applicationId, String name) {
        return count(QueryWrapper.create().ne(SysMenu::getId, id, id != null)
                .eq(SysMenu::getSubSystemId, applicationId)
                .in(SysMenu::getResourceType, ResourceTypeEnum.MENU.getCode())
                .eq(SysMenu::getName, name)) > 0;
    }

    private void fill(SysMenu node, SysMenu parent) {
        node.setParentId(parent == null ? FsTreeUtil.DEF_PARENT_ID : parent.getId());
        node.setTreeGrade(parent == null ? FsTreeUtil.TREE_GRADE : parent.getTreeGrade() + 1);
        node.setTreePath(parent == null ? FsTreeUtil.TREE_SPLIT : FsTreeUtil.getTreePath(parent.getTreePath(), parent.getId()));
    }

    private void fill(SysMenu resource) {
        if (resource.getParentId() == null || resource.getParentId() <= 0) {
            resource.setParentId(FsTreeUtil.DEF_PARENT_ID);
            resource.setTreePath(FsTreeUtil.TREE_SPLIT);
            resource.setTreeGrade(FsTreeUtil.TREE_GRADE);
        } else {
            SysMenu parent = getByIdCache(resource.getParentId());
            ArgumentAssert.notNull(parent, "请正确填写父级");

            resource.setTreeGrade(parent.getTreeGrade() + 1);
            resource.setTreePath(FsTreeUtil.getTreePath(parent.getTreePath(), parent.getId()));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveWithCache(SysMenuDto data) {
        if (StrUtil.containsAny(data.getResourceType(), ResourceTypeEnum.MENU.getCode())) {
            ArgumentAssert.notEmpty(data.getPath(), "请填写【地址栏路径】");
            ArgumentAssert.notEmpty(data.getComponent(), "请填写【页面路径】");
            ArgumentAssert.isFalse(checkName(null, data.getSubSystemId(), data.getName()), "【名称】:{}重复", data.getName());
            if (!ValidatorUtil.isUrl(data.getPath())) {
                ArgumentAssert.isFalse(checkPath(null, data.getSubSystemId(), data.getPath()), "【地址栏路径】:{}重复", data.getPath());
            }
        }
        ArgumentAssert.isFalse(check(null, data.getCode()), "【编码】：{}重复", data.getCode());
        data.setMetaJson(parseMetaJson(data.getMetaJson()));
        SysMenu resource = BeanUtil.toBean(data, SysMenu.class);

        SysMenu parent = getById(data.getParentId());
        if (parent != null) {
            if (ResourceTypeEnum.MENU.eq(data.getResourceType())) {
                ArgumentAssert.isFalse(!ResourceTypeEnum.MENU.eq(parent.getResourceType()),
                        "菜单只能挂载在菜单下级");
                ArgumentAssert.isFalse(parent.getIsHidden() != null && parent.getIsHidden(),
                        "菜单不能挂载在隐藏菜单下级");
            }
        }

        checkGeneral(resource, false);

        fill(resource);
        save(resource);
//        saveResourceApi(resource.getId(), data.getResourceApiList());

        // 淘汰资源下绑定的接口
//        cacheOps.del(ResourceResourceApiCacheKeyBuilder.builder(resource.getId()));
        return resource.getId();
    }

    private String parseMetaJson(String metaJson) {
        if (StrUtil.isNotEmpty(metaJson)) {
            try {
                String json = JsonUtil.toJson(JsonUtil.parse(metaJson, HashMap.class));
                return json == null ? StrPool.EMPTY : json;
            } catch (Exception e) {
                throw new BizException("【元数据】须满足JSON格式");
            }
        }
        return StrPool.EMPTY;
    }

    private void checkGeneral(SysMenu data, boolean isUpdate) {
        final boolean isGeneral = data.getIsGeneral() != null && data.getIsGeneral();
        boolean state = data.getState() == null || data.getState();

        // isGeneral 子节点 改成是，父节点全是
        if (!FsTreeUtil.isRoot(data.getParentId())) {
            SysMenu parent = getById(data.getParentId());
            ArgumentAssert.notNull(parent, "父节点不存在");
            if (isGeneral) {
                ArgumentAssert.isTrue(parent.getIsGeneral(), "请先将父节点【{}】-“公共资源”字段修改为：“是”，在修改本节点", parent.getName());
            }
            if (state) {
                ArgumentAssert.isTrue(parent.getState(), "请先将父节点【{}】-“状态”字段修改为：“启用”，在修改本节点", parent.getName());
            }
        }

        if (isUpdate) {
            // isGeneral 父节点 改成否，子节点全否
            List<SysMenu> childrenList = findChildrenByParentId(data.getId());
            if (CollUtil.isNotEmpty(childrenList)) {
                childrenList.forEach(item -> {
                    if (!isGeneral) {

                        ArgumentAssert.isFalse(item.getIsGeneral(), "请将所有子节点的“公共资源”修改为”否”后，在修改当前节点", item.getName());
                    }
                    if (!state) {
                        ArgumentAssert.isFalse(item.getState(), "请将所有子节点的“状态”修改为“禁用”后，在修改当前节点");
                    }
                });
            }
        }
    }

    @Override
    public List<SysMenu> findChildrenByParentId(Long parentId) {
        ArgumentAssert.notNull(parentId, "parentId 不能为空");
        return list(QueryWrapper.create().in(SysMenu::getParentId, parentId).orderBy(SysMenu::getWeight, true));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long updateWithCacheById(SysMenuDto data) {
        if (StrUtil.containsAny(data.getResourceType(), ResourceTypeEnum.MENU.getCode())) {
            ArgumentAssert.notEmpty(data.getPath(), "【地址栏路径】不能为空");
            ArgumentAssert.notEmpty(data.getComponent(), "【页面路径】不能为空");
            ArgumentAssert.isFalse(checkName(data.getId(), data.getSubSystemId(), data.getName()), "【资源名称】:{}重复", data.getName());
            if (!ValidatorUtil.isUrl(data.getPath())) {
                ArgumentAssert.isFalse(checkPath(data.getId(), data.getSubSystemId(), data.getPath()), "【地址栏路径】:{}重复", data.getPath());
            }
        }
        ArgumentAssert.isFalse(check(data.getId(), data.getCode()), "【编码】:{}重复", data.getCode());
        data.setMetaJson(parseMetaJson(data.getMetaJson()));

        SysMenu resource = BeanPlusUtil.toBean(data, SysMenu.class);
        checkGeneral(resource, true);
        fill(resource);
        updateById(resource);
//        defResourceApiManager.removeByResourceId(Collections.singletonList(resource.getId()));
//        saveResourceApi(resource.getId(), data.getResourceApiList());

        // 淘汰资源下绑定的接口
//        cacheOps.del(ResourceResourceApiCacheKeyBuilder.builder(resource.getId()));
        return resource.getId();
    }

}
