package top.fsfsfs.main.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.main.system.dto.SysMenuDto;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.query.SysMenuQuery;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuVo;

/**
 * 菜单 控制层。
 *
 * @author tangyh
 * @since 2024-07-23 19:20:21
 */
@RestController
@Validated
@Tag(name = "菜单接口")
@RequestMapping("/main/system/sysMenu")
public class SysMenuController extends SuperTreeController<SysMenuService, Long, SysMenu, SysMenuDto, SysMenuQuery, SysMenuVo> {
}
