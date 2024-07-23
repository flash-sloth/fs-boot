package top.fsfsfs.main.system.service;

import cn.hutool.core.lang.tree.Tree;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.system.dto.SysMenuDto;
import top.fsfsfs.main.system.entity.SysMenu;

import java.util.List;

/**
 * 菜单 服务层。
 *
 * @author tangyh
 * @since 2024-07-23 19:20:21
 */
public interface SysMenuService extends SuperService<SysMenu> {

    /**
     * 查询用户拥有的路由
     *
     * @return 路由
     */
    List<Tree<Long>> listVisibleRouter();

    /**
     * 根据id，查询子菜单
     * @param parentId 父菜单ID
     *
     * @return 子菜单
     */
    List<SysMenu> findChildrenByParentId(Long parentId);

    /**
     * 检测资源编码是否可用
     *
     * @param id   资源id
     * @param code 资源编码
     * @return java.lang.Boolean
     * @author tangyh
     */
    Boolean check(Long id, String code);

    /**
     * 检测资源 path 是否重复
     *
     * @param id            主键
     * @param applicationId 应用ID
     * @param path          菜单或视图 地址栏地址
     * @return java.lang.Boolean
     * @author tangyh
     */
    Boolean checkPath(Long id, Long applicationId, String path);

    /**
     * 检测资源 名称 是否重复
     *
     * @param id            主键
     * @param applicationId 应用ID
     * @param name          菜单或视图 名称
     * @return java.lang.Boolean
     * @author tangyh
     */
    Boolean checkName(Long id, Long applicationId, String name);

    /**
     * 保存
     *
     * @param resource 资源
     * @return id
     */
    Long saveWithCache(SysMenuDto resource);

    /**
     * 修改资源并淘汰相关缓存
     *
     * @param data 资源数据
     * @return id
     */
    Long updateWithCacheById(SysMenuDto data);


}
