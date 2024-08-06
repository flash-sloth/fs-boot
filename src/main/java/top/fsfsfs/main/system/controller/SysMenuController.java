package top.fsfsfs.main.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.parser.NodeParser;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.annotation.log.WebLog;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.main.system.dto.SysMenuDto;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.query.SysMenuQuery;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuQueryVo;
import top.fsfsfs.main.system.vo.SysMenuVo;
import top.fsfsfs.util.utils.BeanPlusUtil;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.util.List;

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

    @Override
    public R<Long> handlerSave(SysMenuDto data) {
        return success(superService.saveWithCache(data));
    }

    @Override
    public R<Long> handlerUpdate(SysMenuDto data) {
        return success(superService.updateWithCacheById(data));
    }


    @Operation(
            summary = "按树结构查询"
    )
    @PostMapping({"/menuTree"})
    @WebLog("按树结构查询")
    public  R<List<Tree<Long>>> tree(@RequestBody SysMenuQueryVo pageQuery) {
        return this.success(superService.menuTree(pageQuery));
    }
}
