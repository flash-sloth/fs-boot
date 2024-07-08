package top.fsfsfs.main.system.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.IdUtil;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.basic.mvcflex.request.PageParams;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.main.system.dto.SysParamDto;
import top.fsfsfs.main.system.entity.SysParam;
import top.fsfsfs.main.system.query.SysParamQuery;
import top.fsfsfs.main.system.service.SysParamService;
import top.fsfsfs.main.system.vo.SysParamVo;

/**
 * 系统参数 控制层。
 *
 * @author hukunzhen
 * @since 2024-07-08 21:45:18
 */
@RestController
@Validated
@Tag(name = "系统参数接口")
@RequestMapping("/main/system/sysParam")
public class SysParamController extends SuperTreeController<SysParamService, Long, SysParam, SysParamDto, SysParamQuery, SysParamVo> {
    @Autowired
    private SysParamService sysParamService;

    /**
     * 添加系统参数。
     *
     * @param dto 系统参数
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping
    @Operation(summary="新增", description="保存系统参数")
    public R<Long> save(@Validated @RequestBody @Parameter(description="系统参数") SysParamDto dto) {
        if(sysParamService.getSysParam(dto.getKey()) != null){
            return R.fail( "Key["+dto.getKey()+"]已经存在", dto);
        }
        SysParam sysParam =   BeanUtil.toBean( dto, SysParam.class);
        sysParam.setId(IdUtil.getSnowflakeNextId());
        //sysParam.setCreatedAt(LocalDateTimeUtil.now());
        //sysParam.setUpdatedAt(LocalDateTimeUtil.now());
        if(StringUtils.isEmpty( dto.getContent() )){
            sysParam.setContent("{}");
        }
        //return R.success(sysParamService.saveDto(dto).getId());
        boolean bRet = sysParamService.save(sysParam);
        sysParamService.RefreshSysParamCache();//刷新缓存
        return bRet ? R.success(sysParam.getId()) : R.fail("保存失败",dto);
    }

    /**
     * 根据主键删除系统参数。
     *
     * @param ids 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping
    @Operation(summary="删除", description="根据主键删除系统参数")
    public R<Boolean> delete(@RequestBody List<Long> ids) {
        return R.success(sysParamService.removeByIds(ids));
    }

    /**
     * 根据主键更新系统参数。
     *
     * @param dto 系统参数
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping
    @Operation(summary="修改", description="根据主键更新系统参数")
    public R<Long> update(@Validated(BaseEntity.Update.class) @RequestBody @Parameter(description="系统参数主键")SysParamDto dto) {
        return R.success(sysParamService.updateDtoById(dto).getId());
    }

    /**
     * 根据系统参数主键获取详细信息。
     *
     * @param id 系统参数主键
     * @return 系统参数详情
     */
    @GetMapping("/{id}")
    @Operation(summary="单体查询", description="根据主键获取系统参数")
    public R<SysParamVo> get(@PathVariable Long id) {
        SysParam entity = sysParamService.getById(id);
        return R.success(BeanUtil.toBean(entity, SysParamVo.class));
    }

    /**
     * 分页查询系统参数。
     *
     * @param params 分页对象
     * @return 分页对象
     */
    @PostMapping("/page")
    @Operation(summary="分页列表查询", description="分页查询系统参数")
    public R<Page<SysParamVo>> page(@RequestBody @Validated  PageParams<SysParamQuery> params) {
        Page<SysParamVo> page = Page.of(params.getCurrent(), params.getSize());
        SysParam entity = BeanUtil.toBean(params.getModel(), SysParam.class);
        QueryWrapper wrapper = QueryWrapper.create(params.getModel(), ControllerUtil.buildOperators(entity.getClass()));
        ControllerUtil.buildOrder(wrapper, params, entity.getClass());
        sysParamService.pageAs(page, wrapper, SysParamVo.class);
        return R.success(page);
        // Page<SysParam> page = Page.of(params.getCurrent(), params.getSize());
        // sysParamService.page(page, wrapper);
        // Page<SysParamVo> voPage = top.fsfsfs.basic.mybatisflex.utils.BeanPageUtil.toBeanPage(page, SysParamVo.class);
        // return R.success(voPage);
    }
}
