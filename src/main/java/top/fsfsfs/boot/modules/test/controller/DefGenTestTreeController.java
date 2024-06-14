package top.fsfsfs.boot.modules.test.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.boot.modules.test.entity.DefGenTestTree;
import top.fsfsfs.boot.modules.test.service.DefGenTestTreeService;
import top.fsfsfs.boot.modules.test.vo.DefGenTestTreeQueryVO;
import top.fsfsfs.boot.modules.test.vo.DefGenTestTreeResultVO;
import top.fsfsfs.boot.modules.test.vo.DefGenTestTreeVO;
import top.fsfsfs.util.utils.FsTreeUtil;

import java.util.List;

@RestController
@RequestMapping("/system/super")
@RequiredArgsConstructor
@Tag(name = "tree无缓存CRUD")
@Slf4j
public class DefGenTestTreeController extends SuperController<DefGenTestTreeService, Long,
        DefGenTestTree, DefGenTestTreeVO, DefGenTestTreeQueryVO, DefGenTestTreeResultVO> {


    @Operation(summary = "按树结构查询")
    @PostMapping("/tree")
    public R<List<Tree<Long>>> tree(@RequestBody DefGenTestTreeQueryVO pageQuery) {

        List<DefGenTestTree> list = superService.list();
        List<DefGenTestTreeResultVO> treeList = BeanUtil.copyToList(list, DefGenTestTreeResultVO.class);

        List<Tree<Long>> list3 = FsTreeUtil.build(treeList);

        return success(list3);
    }


}
