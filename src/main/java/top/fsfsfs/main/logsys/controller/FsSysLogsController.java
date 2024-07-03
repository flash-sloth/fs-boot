package top.fsfsfs.main.logsys.controller;

import cn.hutool.core.bean.BeanUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.request.PageParams;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.main.logsys.dto.FsSysLogsDto;
import top.fsfsfs.main.logsys.entity.FsSysLogs;
import top.fsfsfs.main.logsys.query.FsSysLogsQuery;
import top.fsfsfs.main.logsys.service.FsSysLogsService;
import top.fsfsfs.main.logsys.vo.FsSysLogsVo;

/**
 * 系统日志 控制层。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */

@RestController
@Validated
@Tag(name = "系统日志接口")
@RequestMapping("/main/logsys/fsSysLogs")
public class FsSysLogsController extends SuperController<FsSysLogsService, Long, FsSysLogs, FsSysLogsDto, FsSysLogsQuery, FsSysLogsVo> {
    @Autowired
    private FsSysLogsService fsSysLogsService;

    /**
     * 添加系统日志。
     *
     * @param dto 系统日志
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping
    @Operation(summary="新增", description="保存系统日志")
    public R<Long> save(@Validated @RequestBody @Parameter(description="系统日志") FsSysLogsDto dto) {
        return R.success(fsSysLogsService.saveDto(dto).getId());
    }

    /**
     * 根据主键删除系统日志。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping
    @Operation(summary="删除", description="根据主键删除系统日志")
    public R<Boolean> delete(@RequestBody List<Long> ids) {
        return R.success(fsSysLogsService.removeByIds(ids));
    }

    /**
     * 根据主键更新系统日志。
     *
     * @param dto 系统日志
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping
    @Operation(summary="修改", description="根据主键更新系统日志")
    public R<Long> update(@Validated(BaseEntity.Update.class) @RequestBody @Parameter(description="系统日志主键")FsSysLogsDto dto) {
        return R.success(fsSysLogsService.updateDtoById(dto).getId());
    }

    /**
     * 根据系统日志主键获取详细信息。
     *
     * @param id 系统日志主键
     * @return 系统日志详情
     */
    @GetMapping("/{id}")
    @Operation(summary="单体查询", description="根据主键获取系统日志")
    public R<FsSysLogsVo> get(@PathVariable Long id) {
        FsSysLogs entity = fsSysLogsService.getById(id);
        return R.success(BeanUtil.toBean(entity, FsSysLogsVo.class));
    }

    /**
     * 分页查询系统日志。
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @PostMapping("/page")
    @Operation(summary="分页列表查询", description="分页查询系统日志")
    public R<Page<FsSysLogsVo>> page(@RequestBody @Validated  PageParams<FsSysLogsQuery> params) {
        Page<FsSysLogsVo> page = Page.of(params.getCurrent(), params.getSize());
        QueryWrapper wrapper = QueryWrapper.create(params.getModel(), ControllerUtil.buildOperators(params.getModel().getClass()));
        ControllerUtil.buildOrder(wrapper, params);
        fsSysLogsService.pageAs(page, wrapper, FsSysLogsVo.class);
        return R.success(page);
        // Page<FsSysLogs> page = Page.of(params.getCurrent(), params.getSize());
        // fsSysLogsService.page(page, wrapper);
        // Page<FsSysLogsVo> voPage = top.fsfsfs.basic.mybatisflex.utils.BeanPageUtil.toBeanPage(page, FsSysLogsVo.class);
        // return R.success(voPage);
    }
}
