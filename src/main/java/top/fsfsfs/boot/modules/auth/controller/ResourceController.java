package top.fsfsfs.boot.modules.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.boot.modules.auth.vo.VisibleResourceVO;
import top.fsfsfs.boot.modules.system.service.SysMenuService;

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


}
