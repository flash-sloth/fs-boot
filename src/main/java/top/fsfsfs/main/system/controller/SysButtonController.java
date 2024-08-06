package top.fsfsfs.main.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.system.dto.SysButtonDto;
import top.fsfsfs.main.system.entity.SysButton;
import top.fsfsfs.main.system.query.SysButtonQuery;
import top.fsfsfs.main.system.service.SysButtonService;
import top.fsfsfs.main.system.vo.SysButtonVo;

/**
 * 按钮 控制层。
 *
 * @author liyeo
 * @since 2024-08-06 23:02:54
 */
@RestController
@Validated
@Tag(name = "按钮接口")
@RequestMapping("/main/system//sysButton")
public class SysButtonController extends SuperController<SysButtonService, Long, SysButton, SysButtonDto, SysButtonQuery, SysButtonVo> {
}
