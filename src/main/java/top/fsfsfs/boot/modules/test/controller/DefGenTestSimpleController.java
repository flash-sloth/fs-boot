package top.fsfsfs.boot.modules.test.controller;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.mvcflex.request.PageFlexUtil;
import top.fsfsfs.basic.mvcflex.request.PageParams;
import top.fsfsfs.boot.modules.test.entity.DefGenTestSimple;
import top.fsfsfs.boot.modules.test.service.DefGenTestSimpleService;
import top.fsfsfs.boot.modules.test.vo.DefGenTestSimpleQueryVO;
import top.fsfsfs.boot.modules.test.vo.DefGenTestSimpleResultVO;
import top.fsfsfs.boot.modules.test.vo.DefGenTestSimpleVO;

import java.util.List;

@RestController
@RequestMapping("/system/test2")
@RequiredArgsConstructor
@Tag(name = "simple不继承父类CRUD")
@Slf4j
public class DefGenTestSimpleController {

    private final DefGenTestSimpleService testSimpleService;

    @Operation(summary = "删除")
    @DeleteMapping
    public R<Boolean> delete(@RequestBody List<Long> ids) {
        return R.success(testSimpleService.removeByIds(ids));
    }

    @GetMapping("/list")
    public R<Object> list() {
        return R.success(testSimpleService.list());
    }

    /**
     * 分页查询
     *
     * @param params 分页参数
     * @return 分页数据s
     */
    @Operation(summary = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<DefGenTestSimpleResultVO>> page(@RequestBody @Validated PageParams<DefGenTestSimpleQueryVO> params) {
        Page<DefGenTestSimple> page = Page.of(params.getCurrent(), params.getSize());
        QueryWrapper wrapper = QueryWrapper.create(params.getModel());

        Page<DefGenTestSimple> page1 = testSimpleService.page(page, wrapper);
        Page<DefGenTestSimpleResultVO> resultPage = PageFlexUtil.toBeanPage(page1, DefGenTestSimpleResultVO.class);
        return R.success(resultPage);
    }

    @Operation(summary = "分页列表查询")
    @PostMapping(value = "/pageAs")
    public R<Page<DefGenTestSimpleResultVO>> pageAs(@RequestBody @Validated PageParams<DefGenTestSimpleQueryVO> params) {
        Page<DefGenTestSimpleResultVO> page = Page.of(params.getCurrent(), params.getSize());
        QueryWrapper wrapper = QueryWrapper.create(params.getModel());
        Page<DefGenTestSimpleResultVO> page1 = testSimpleService.pageAs(page, wrapper, DefGenTestSimpleResultVO.class);
        return R.success(page1);
    }


    @Operation(summary = "保存")
    @PostMapping("/save")
    public R<DefGenTestSimple> save(@Validated @RequestBody DefGenTestSimpleVO simple) {
        return R.success(testSimpleService.saveVo(simple));
    }

    @Operation(summary = "修改")
    @PostMapping("/updateById")
    public R<DefGenTestSimple> updateById(@Validated(BaseEntity.Update.class) @RequestBody DefGenTestSimpleVO simple) {
        return R.success(testSimpleService.updateVoById(simple));
    }

    @Operation(summary = "获取详情")
    @GetMapping("/get")
    public R<DefGenTestSimple> getById(@RequestParam Long id) {
        return R.success(testSimpleService.getById(id));
    }

}
