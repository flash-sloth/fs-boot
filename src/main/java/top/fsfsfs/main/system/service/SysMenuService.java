package top.fsfsfs.main.system.service;

import cn.hutool.core.lang.tree.Tree;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.system.entity.SysMenu;

import java.util.List;

/**
 * 菜单 服务层。
 *
 * @author tangyh
 * @since 2024-06-15
 */
public interface SysMenuService extends SuperService<SysMenu> {

    /**
     * 查询用户拥有的路由
     *
     * @return 路由
     */
    List<Tree<Long>> listVisibleRouter();
}
