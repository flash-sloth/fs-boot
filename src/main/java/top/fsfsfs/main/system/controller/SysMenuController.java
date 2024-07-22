package top.fsfsfs.main.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.annotation.log.WebLog;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.mvcflex.controller.PageController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.main.system.entity.SysMenu;
import top.fsfsfs.main.system.service.SysMenuService;
import top.fsfsfs.main.system.vo.SysMenuQueryVo;
import top.fsfsfs.main.system.vo.SysMenuResultVo;
import top.fsfsfs.main.system.vo.SysMenuVo;
import top.fsfsfs.util.utils.BeanPlusUtil;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单 控制层。
 *
 * @author tangyh
 * @since 2024-06-15
 */
@RestController
@RequestMapping("/main/sysMenu")
public class SysMenuController extends SuperTreeController<SysMenuService, Long, SysMenu, SysMenuVo, SysMenuQueryVo, SysMenuResultVo> {


}
