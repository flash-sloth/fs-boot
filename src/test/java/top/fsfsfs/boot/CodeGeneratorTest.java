package top.fsfsfs.boot;

import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory;
import org.junit.jupiter.api.Test;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.base.entity.TreeEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.controller.SuperReadController;
import top.fsfsfs.basic.mvcflex.controller.SuperSimpleController;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.basic.mvcflex.controller.SuperWriteController;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.common.enumeration.Sex;
import top.fsfsfs.boot.modules.test.entity.DefGenTestTree;

import java.io.Serializable;

public class CodeGeneratorTest {

    @Test
    public void test1(){
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(DefGenTestTree.class);
        System.out.println(tableInfo);
    }
    @Test
    public void test2(){
        StringBuilder genericityStr = new StringBuilder("aaa , ");
        String s = StrUtil.removeSuffix(genericityStr, ", ");
        System.out.println(s);
    }

    public static void main(String[] args) {
        //配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/flash_sloth?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
//        Map<String, Map<String, String>> preview = generator.preview();
//        System.out.println(preview);
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("com.fsfsfs.boot");
//        globalConfig.setBasePackage("top.fsfsfs.boot.modules.system");

//        globalConfig.setEntityGenerateEnable();
        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("fs_");
        globalConfig.setGenerateTable("fs_sys_menu");

        globalConfig.setJdkVersion(17);

        globalConfig.enableEntity().setSuperClass(TreeEntity.class)
                .setGenericityType(Long.class).setOverwriteEnable(true)
                .setWithLombok(true)
                .setWithBaseClassEnable(true)
        ;

        globalConfig.enableVo()
                .setSuperClass(TreeNode.class).setGenericityType(Long.class)
                .setWithLombok(true)
//                .setImplInterfaces(Serializable.class)
                .setOverwriteEnable(true);

//        globalConfig.enableController()
//                .setSuperClass(SuperController.class)
////                .setSuperClass(SuperWriteController.class)
////                .setSuperClass(SuperReadController.class)
////                .setSuperClass(SuperTreeController.class)
////                .setSuperClass(SuperSimpleController.class)
//                .setOverwriteEnable(true);
//        //设置生成 mapper
//        globalConfig.enableMapper().setSuperClass(SuperMapper.class).setOverwriteEnable(true);
//
//        globalConfig.enableService().setSuperClass(SuperService.class).setOverwriteEnable(true);
//        globalConfig.enableServiceImpl().setSuperClass(SuperServiceImpl.class).setOverwriteEnable(true);

        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.setColumnConfig("fs_gen_test_tree", columnConfig);

        JdbcTypeMapping.setTypeMapper(new JdbcTypeMapping.JdbcTypeMapper() {
            @Override
            public String getType(String jdbcType, Table table, Column column) {
                if (column.getName().equals("test7")) {
                    return Sex.class.getName();
                }
                return null;
            }
        });


//        globalConfig.setTemplateEngine();
//        GeneratorFactory.registerGenerator("vo", new VoGenerator());

        return globalConfig;
    }

}
