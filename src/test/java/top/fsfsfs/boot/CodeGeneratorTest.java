package top.fsfsfs.boot;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory;
import org.junit.jupiter.api.Test;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.boot.common.enumeration.Sex;
import top.fsfsfs.boot.modules.test.entity.DefGenTestTree;

public class CodeGeneratorTest {

    @Test
    public void test1() {
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(DefGenTestTree.class);
        System.out.println(tableInfo);
    }

    @Test
    public void test3() {
        System.out.println(Integer.MAX_VALUE);
    }

    @Test
    public void test2() {
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
//        globalConfig.setBasePackage("top.fsfsfs.boot.modules.generator");

//        globalConfig.setEntityGenerateEnable();
        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("fs_");
        globalConfig.setGenerateTable("fs_gen_test_simple");
//        globalConfig.setGenerateTable("fs_code_creator_column");
//        globalConfig.setGenerateTable("fs_code_creator", "fs_code_creator_column");
//        globalConfig.setGenerateTable("fs_sys_menu");
        globalConfig.setJdkVersion(17);
        globalConfig.getJavadocConfig()
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""));
//        globalConfig.getTableConfig(TableConfig.ALL_TABLES)
//                .setInsertListenerClass(DefaultInsertListener.class)
//                .setUpdateListenerClass(DefaultUpdateListener.class)
//        ;
        globalConfig.setTableConfig(TableConfig.create()
//                        .setTableName("fs_code_creator_column")
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        globalConfig.enableEntity().setSuperClass(SuperEntity.class)
                .setGenericityType(Long.class).setOverwriteEnable(true)
                .setWithLombok(true)
                .setWithBaseClassEnable(true)
        ;

//        globalConfig.enableVo()
////                .setSuperClass(TreeNode.class).setGenericityType(Long.class)
//                .setWithLombok(true).setWithSwagger(true).setWithExcel(true)
////                .setImplInterfaces(Serializable.class)
//                .setOverwriteEnable(true);
//
//        globalConfig.enableQuery()
//                .setWithLombok(true).setWithSwagger(true).setWithExcel(true)
////                .setImplInterfaces(Serializable.class)
//                .setOverwriteEnable(true);
//
//        globalConfig.enableDto()
//                .setWithLombok(true).setWithSwagger(true).setWithValidator(true)
////                .setImplInterfaces(Serializable.class)
//                .setOverwriteEnable(true)
//                .setIgnoreColumns(SuperEntity.CREATED_AT_FIELD, SuperEntity.CREATED_BY_FIELD,
//                        SuperEntity.UPDATED_AT_FIELD, SuperEntity.UPDATED_BY_FIELD,
//                        SuperEntity.DELETED_AT_FIELD, SuperEntity.DELETED_BY_FIELD)
//        ;

//        globalConfig.enableController()
////                .setWithCrud(true)
//                .setSuperClass(SuperController.class)
////                .setSuperClass(SuperWriteController.class)
////                .setSuperClass(SuperReadController.class)
////                .setSuperClass(SuperTreeController.class)
////                .setSuperClass(SuperSimpleController.class)
//                .setOverwriteEnable(true);
//
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
                if (column.getName().equals("test1111")) {
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
