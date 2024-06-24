package top.fsfsfs.boot.modules.generator.vo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.parser.NodeParser;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 预览代码
 * @author tangyh
 * @since 2024/6/23 22:38
 */
@Data
@Accessors(chain = true)
public class PreviewVo extends TreeNode<Long> {
    //        private Long id;
//        private Integer weight;
    //        private Long parentId;
//        private String name;
    private String path;
    private String type;
    private String content;
    private Boolean isReadonly;


    public static class PreviewNodeParser implements NodeParser<PreviewVo, Long> {
        @Override
        public void parse(PreviewVo treeNode, Tree<Long> tree) {
            Map<String, Object> map = BeanUtil.beanToMap(treeNode);
            tree.putAll(map);

            tree.setId(treeNode.getId());
            tree.setParentId(treeNode.getParentId());
            tree.setWeight(treeNode.getWeight());
            tree.setName(treeNode.getName());
        }
    }
}