package top.fsfsfs.boot;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.ParameterizedTypeImpl;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.TypeUtil;
import org.junit.jupiter.api.Test;
import top.fsfsfs.basic.mvcflex.controller.SuperController;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.List;

public class TreeTest {
    @Test
    public void test() {
        // 构建node列表
        List<TreeNode<String>> nodeList = CollUtil.newArrayList();

        nodeList.add(new TreeNode<>("1", "0", "系统管理", 5).setExtra(MapUtil.<String, Object>builder("aaa", "aaaa").build()));
        nodeList.add(new TreeNode<>("11", "1", "用户管理", 222222));
        nodeList.add(new TreeNode<>("111", "11", "用户添加", 0));
        nodeList.add(new TreeNode<>("2", "0", "店铺管理", 1));
        nodeList.add(new TreeNode<>("21", "2", "商品管理", 44));
        nodeList.add(new TreeNode<>("221", "2", "商品管理2", 2));


        List<Tree<String>> treeList = TreeUtil.build(nodeList, "0");

        System.out.println(treeList);
    }

    @Test
    public void test2(){
        Class<?> superClass = SuperController.class;


        Type[] typeArguments = TypeUtil.getTypeArguments(superClass);
        Type typeArgument = TypeUtil.getTypeArgument(superClass, 1);

        ParameterizedType parameterizedType = TypeUtil.toParameterizedType(superClass.getTypeParameters()[0].getBounds()[0]);

        TypeVariable<? extends Class<?>>[] typeParameters = superClass.getTypeParameters();

//        System.out.println(typeParameters);
        System.out.println("");

    }


}
