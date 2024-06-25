package top.fsfsfs.main.system.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuQueryVo;
import top.fsfsfs.main.system.vo.SysMenuResultVo;
import top.fsfsfs.main.system.vo.SysMenuVo;

/**
 * 菜单 控制层。
 *
 * @author tangyh
 * @since 2024-06-15
 */
@RestController
@RequestMapping("/main/sysMenu")
public class SysMenuController extends SuperController<SysMenuService, Long, SysMenu, SysMenuVo, SysMenuQueryVo, SysMenuResultVo> {


}
