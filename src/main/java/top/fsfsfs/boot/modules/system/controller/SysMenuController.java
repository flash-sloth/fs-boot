package top.fsfsfs.boot.modules.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.boot.modules.system.entity.SysMenu;
import top.fsfsfs.boot.modules.system.service.SysMenuService;
import top.fsfsfs.boot.modules.system.vo.SysMenuQueryVo;
import top.fsfsfs.boot.modules.system.vo.SysMenuResultVo;
import top.fsfsfs.boot.modules.system.vo.SysMenuVo;

/**
 * 菜单 控制层。
 *
 * @author tangyh
 * @since 2024-06-15
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController extends SuperController<SysMenuService, Long, SysMenu, SysMenuVo, SysMenuQueryVo, SysMenuResultVo> {


}
