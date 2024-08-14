package top.fsfsfs.main.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.StrUtil;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.fsfsfs.basic.exception.BizException;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.common.enumeration.system.ResourceTypeEnum;
import top.fsfsfs.main.auth.vo.RouterMeta;
import top.fsfsfs.main.system.dto.SysMenuDto;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.mapper.SysMenuMapper;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuQueryVo;
import top.fsfsfs.main.system.vo.SysMenuVo;
import top.fsfsfs.util.utils.ArgumentAssert;
import top.fsfsfs.util.utils.BeanPlusUtil;
import top.fsfsfs.util.utils.FsTreeUtil;
import top.fsfsfs.util.utils.JsonUtil;
import top.fsfsfs.util.utils.ValidatorUtil;

import java.util.Arrays;
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
        // 1 查询登录用户拥有的菜单 （目前查询全部，后续修改）
        List<SysMenu> menuList = list();
        List<SysMenuVo> resultList = BeanUtil.copyToList(menuList, SysMenuVo.class);

//        2 转换树结构， hutool的工具类性能好一些
        List<Tree<Long>> menuTreeList = FsTreeUtil.build(resultList, new RouterNodeParser());

//        3 构造前端需要的vueRouter数据，此方法可以考虑在前端做
        forEachTree(menuTreeList, 1, null);
        return menuTreeList;
    }

    public static class RouterNodeParser implements NodeParser<SysMenuVo, Long> {
        @Override
        public void parse(SysMenuVo treeNode, Tree<Long> tree) {
            tree.putExtra("node", treeNode);
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
            SysMenuVo node = (SysMenuVo) item.get("node");
            RouterMeta meta = null;

            if (item.get(META) == null) {
                meta = new RouterMeta();
                meta.setComponent(node.getComponent());
                meta.setTitle(StrUtil.isEmpty(node.getTitle()) ? node.getName() : node.getTitle());
                meta.setIcon(node.getIcon());
                meta.setHideInMenu(node.getHideInMenu() != null && node.getHideInMenu());
                if (meta.getHideInMenu()) {
                    meta.setActiveMenu(parent.getName().toString());
                }

                if (ResourceTypeEnum.INNER_HREF.eq(node.getMenuType())) {
                    // TODO 这里要不要改为其他？
                    meta.setHref(node.getHref());
                    meta.setComponent("_builtin/iframe/index");
                    node.setComponent("_builtin/iframe/index");
                } else if (ResourceTypeEnum.OUTER_HREF.eq(node.getMenuType())) {
                    meta.setHref(node.getHref());
                    node.setComponent("IFRAME");
                    node.setPath("/IFRAME");
                }

            } else {
                meta = (RouterMeta) item.get(META);
            }

            // soybean-admin 要求的格式
            if (parent != null) {
                item.setName(parent.getName() + "_" + item.getName());
            }

            item.put(META, meta);

            if (CollUtil.isNotEmpty(item.getChildren())) {
                forEachTree(item.getChildren(), level + 1, item);
            }
            // 将node 平铺到tree节点
            item.remove("node");
            Map<String, Object> map = BeanUtil.beanToMap(node);
            // 避免name字段 soybean-admin 逻辑被覆盖
            map.remove("name");
            item.putAll(map);
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
        ArgumentAssert.isFalse(check(null, data.getCode()), "编码重复：{}", data.getCode());
        ArgumentAssert.isFalse(checkName(null, data.getSubSystemId(), data.getName()), "名称重复：{}", data.getName());

        if (data.getHideInMenu() == null) {
            data.setHideInMenu(false);
        }

        if (!StrUtil.equals(data.getMenuType(), ResourceTypeEnum.OUTER_HREF.getCode())) {
            ArgumentAssert.notEmpty(data.getPath(), "请填写【地址栏路径】");
            ArgumentAssert.isFalse(checkPath(null, data.getSubSystemId(), data.getPath()), "【地址栏路径】:{}重复", data.getPath());
        }
        SysMenu parent = data.getParentId() != null ? getById(data.getParentId()) : null;

        if (StrUtil.equals(data.getMenuType(), ResourceTypeEnum.MENU.getCode())) {
            ArgumentAssert.notEmpty(data.getComponent(), "请填写【页面路径】");
            if (parent != null) {
                if (data.getHideInMenu()) {
                    ArgumentAssert.contain(Arrays.asList(ResourceTypeEnum.DIR.getCode(), ResourceTypeEnum.MENU.getCode()), parent.getMenuType(), "【隐藏菜单】只能挂载在【{}】或【{}】下级", ResourceTypeEnum.DIR.getDesc(), ResourceTypeEnum.MENU.getDesc());
                } else {
                    ArgumentAssert.equals(ResourceTypeEnum.DIR.getCode(), parent.getMenuType(), "【{}】只能挂载在【{}】下级", ResourceTypeEnum.MENU.getDesc(), ResourceTypeEnum.DIR.getDesc());
                }
            }
        } else if (StrUtil.equals(data.getMenuType(), ResourceTypeEnum.DIR.getCode())) {
            if (parent != null) {
                ArgumentAssert.isTrue(ResourceTypeEnum.DIR.eq(parent.getMenuType()), "【{}】只能挂载在【{}】下级", ResourceTypeEnum.DIR.getDesc(), ResourceTypeEnum.DIR.getDesc());
            }

            data.setComponent("layout.base");
        } else if (StrUtil.equals(data.getMenuType(), ResourceTypeEnum.INNER_HREF.getCode())) {
            if (parent != null) {
                ArgumentAssert.isTrue(ResourceTypeEnum.DIR.eq(parent.getMenuType()), "【{}】只能挂载在【{}】下级", ResourceTypeEnum.INNER_HREF.getDesc(), ResourceTypeEnum.DIR.getDesc());
            }
            ArgumentAssert.notEmpty(data.getHref(), "请填写【跳转地址】");
            data.setComponent("IFRAME");
        } else if (StrUtil.equals(data.getMenuType(), ResourceTypeEnum.OUTER_HREF.getCode())) {
            if (parent != null) {
                ArgumentAssert.isTrue(ResourceTypeEnum.DIR.eq(parent.getMenuType()), "【{}】只能挂载在【{}】下级", ResourceTypeEnum.OUTER_HREF.getDesc(), ResourceTypeEnum.DIR.getDesc());
            }
            ArgumentAssert.notEmpty(data.getHref(), "请填写【跳转地址】");
            data.setComponent("IFRAME");
        }


        data.setMetaJson(parseMetaJson(data.getMetaJson()));
        SysMenu resource = BeanUtil.toBean(data, SysMenu.class);

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
        if (StrUtil.containsAny(data.getMenuType(), ResourceTypeEnum.MENU.getCode())) {
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

    @Override
    public List<Tree<Long>> menuTree(SysMenuQueryVo query) {
        SysMenu entity = BeanPlusUtil.toBean(query, SysMenu.class);
        QueryWrapper wrapper = QueryWrapper.create(entity, ControllerUtil.buildOperators(entity.getClass()));
        List<SysMenu> list = list(wrapper);
        List<SysMenuVo> treeList = BeanUtil.copyToList(list, SysMenuVo.class);
        return FsTreeUtil.build(treeList, new MenuNodeParser());
    }


    public static class MenuNodeParser implements NodeParser<SysMenuVo, Long> {
        @Override
        public void parse(SysMenuVo treeNode, Tree<Long> tree) {
            Map<String, Object> map = BeanUtil.beanToMap(treeNode);
            tree.putAll(map);
        }
    }
}
