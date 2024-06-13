package top.fsfsfs.boot.modules.test.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.lang.tree.parser.NodeParser;
import cn.hutool.core.map.MapUtil;
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

import java.util.List;
import java.util.Map;

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

        List<Tree<Long>> list3 = TreeUtil.build(treeList, null, TreeNodeConfig.DEFAULT_CONFIG, new FsNodeParser<>());

        return success(list3);
    }

    public static class FsNodeParser<T, E extends TreeNode<T>> implements NodeParser<E, T> {
        @Override
        public void parse(E treeNode, Tree<T> tree) {
            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());

            //扩展字段
            final Map<String, Object> extra = treeNode.getExtra();
            if (MapUtil.isNotEmpty(extra)) {
                extra.forEach(tree::putExtra);
            }
        }
    }

}
