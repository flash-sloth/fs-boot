package top.fsfsfs.main.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.main.auth.vo.VisibleResourceVO;
import top.fsfsfs.main.system.service.SysMenuService;

import java.util.Arrays;
import java.util.Collections;


/**
 * @author tangyh
 * @since 2024年06月15日22:19:08
 */
@RestController
@RequestMapping("/anyone")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "鉴权接口")
public class ResourceController {
    private final SysMenuService sysMenuService;

    /**
     * 查询用户可用的所有资源
     *
     */
    @Operation(summary = "查询用户可用的所有资源", description = "根据员工ID查询员工在某个应用下可用的资源")
    @GetMapping("/visible/resource")
    public R<VisibleResourceVO> visible() {
        return R.success(VisibleResourceVO.builder()
                .enabled(true)
                .caseSensitive(true)
                .roleList(Collections.singletonList("PT_ADMIN"))
                .resourceList(Arrays.asList("test:aaa:add", "test:aaa:edit"))
                .routerList(sysMenuService.listVisibleRouter())
                .build());
    }
    /**
     * 根据用户ID和路由名称判断用户是否具有权限
     *
     */
    @Operation(summary = "判断路由是否可用", description = "根据用户ID和路由名称判断用户是否具有权限")
    @GetMapping("/isRouteExist")
    public R<Boolean> isRouteExist(@RequestParam String routeName) {
        // TODO 实现具体判断逻辑
        return R.success(true);
    }



}
