package top.fsfsfs.main.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.util.ObjectUtil;
import org.springframework.stereotype.Service;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.main.auth.vo.RouterMeta;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.mapper.SysMenuMapper;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuResultVo;
import top.fsfsfs.util.utils.FsTreeUtil;
import top.fsfsfs.util.utils.JsonUtil;

import java.util.List;
import java.util.Map;

/**
 * 菜单 服务层实现。
 *
 * @author tangyh
 * @since 2024-06-15
 */
@Service
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


}
