package top.fsfsfs.boot;

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
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.common.enumeration.Sex;
import top.fsfsfs.boot.modules.test.entity.DefGenTestTree;

public class CodeGeneratorTest {

    @Test
    public void test1(){
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(DefGenTestTree.class);
        System.out.println(tableInfo);
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


        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("fs_");
        globalConfig.setGenerateTable("fs_sys_menu");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        globalConfig.setEntitySuperClass(BaseEntity.class);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(17)

        ;
        globalConfig.getJavadocConfig();
        globalConfig.getPackageConfig();
        globalConfig.getStrategyConfig().setIgnoreColumns(BaseEntity.CREATED_BY_FIELD, BaseEntity.CREATED_AT_FIELD);
        globalConfig.getTemplateConfig();

        globalConfig.enableEntity().setSuperClass(TreeEntity.class).setGenericityType(Long.class);
//                .setWithBaseClassEnable(true)
        ;
        globalConfig.enableController().setSuperClass(SuperController.class);
        //设置生成 mapper
        globalConfig.enableMapper().setSuperClass(SuperMapper.class);

        globalConfig.enableService().setSuperClass(SuperService.class);
        globalConfig.enableServiceImpl().setSuperClass(SuperServiceImpl.class);

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
