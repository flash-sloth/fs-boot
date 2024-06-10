package top.fsfsfs.boot.modules.system.controller;

import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.SqlOperators;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.request.PageFlexUtil;
import top.fsfsfs.basic.mvcflex.request.PageParams;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple2;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimpleService;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2QueryVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2ResultVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2VO;

import java.util.List;

import static top.fsfsfs.boot.modules.system.entity.table.DefGenTestSimple2TableDef.DEF_GEN_TEST_SIMPLE2;

@RestController
@RequestMapping("/system/test2")
@RequiredArgsConstructor
@Tag(name = "不继承接口")
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
    public R<Page<DefGenTestSimple2ResultVO>> page(@RequestBody @Validated PageParams<DefGenTestSimple2QueryVO> params) {
        Page<DefGenTestSimple2> page = Page.of(params.getCurrent(), params.getSize());
        QueryWrapper wrapper = QueryWrapper.create(params.getModel());

        Page<DefGenTestSimple2> page1 = testSimpleService.page(page, wrapper);
        Page<DefGenTestSimple2ResultVO> resultPage = PageFlexUtil.toBeanPage(page1, DefGenTestSimple2ResultVO.class);
        return R.success(resultPage);
    }

    @Operation(summary = "分页列表查询")
    @PostMapping(value = "/pageAs")
    public R<Page<DefGenTestSimple2ResultVO>> pageAs(@RequestBody @Validated PageParams<DefGenTestSimple2QueryVO> params) {
        Page<DefGenTestSimple2ResultVO> page = Page.of(params.getCurrent(), params.getSize());
        QueryWrapper wrapper = QueryWrapper.create(params.getModel());
        Page<DefGenTestSimple2ResultVO> page1 = testSimpleService.pageAs(page, wrapper, DefGenTestSimple2ResultVO.class);
        return R.success(page1);
    }


    @Operation(summary = "保存")
    @PostMapping("/save")
    public R<DefGenTestSimple2> save(@Validated @RequestBody DefGenTestSimple2VO simple) {
        return R.success(testSimpleService.saveVo(simple));
    }

    @Operation(summary = "修改")
    @PostMapping("/updateById")
    public R<DefGenTestSimple2> updateById(@Validated(SuperEntity.Update.class) @RequestBody DefGenTestSimple2VO simple) {
        return R.success(testSimpleService.updateVoById(simple));
    }

    @Operation(summary = "获取详情")
    @GetMapping("/get")
    public R<DefGenTestSimple2> getById( @RequestParam Long id) {
        return R.success(testSimpleService.getById(id));
    }

    public static void main(String[] args) {

        DefGenTestSimple2QueryVO vo = new DefGenTestSimple2QueryVO();
        vo.setName("nihao");
        QueryWrapper wrapper = QueryWrapper.create(vo, SqlOperators.of().set(DEF_GEN_TEST_SIMPLE2.NAME, SqlOperator.GE));
        wrapper.select(DEF_GEN_TEST_SIMPLE2.DEFAULT_COLUMNS)
                .from(DefGenTestSimple2QueryVO.class)
//                .where(DEF_GEN_TEST_SIMPLE2.NAME.like("nihao").and(DEF_GEN_TEST_SIMPLE2.TEST11.like("111")))
        ;

        System.out.println(wrapper.toSQL());
    }
}
