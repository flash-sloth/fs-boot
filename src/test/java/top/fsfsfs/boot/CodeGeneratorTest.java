package top.fsfsfs.boot;

import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.ColumnConfig;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.dialect.JdbcTypeMapping;
import com.mybatisflex.codegen.entity.Column;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.common.enumeration.Sex;

public class CodeGeneratorTest {

    public static void main(String[] args) {
        //配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/lamp_column?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();
        //GlobalConfig globalConfig = createGlobalConfigUseStyle2();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.setBasePackage("com.fsfsfs.boot");

        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("def_");
        globalConfig.setGenerateTable("fs_gen_test_tree");

        //设置生成 entity 并启用 Lombok
        globalConfig.setEntityGenerateEnable(true);
        globalConfig.setEntityWithLombok(true);
        globalConfig.setEntitySuperClass(SuperEntity.class);
        //设置项目的JDK版本，项目的JDK为14及以上时建议设置该项，小于14则可以不设置
        globalConfig.setEntityJdkVersion(17)

        ;
        globalConfig.getJavadocConfig();
        globalConfig.getPackageConfig();
        globalConfig.getStrategyConfig().setIgnoreColumns(SuperEntity.CREATED_BY_FIELD, SuperEntity.CREATED_TIME_FIELD);
        globalConfig.getTemplateConfig();

        globalConfig.setCustomConfig("aaa","dddd");

        globalConfig.enableController();
        //设置生成 mapper
        globalConfig.setMapperGenerateEnable(true);

        globalConfig.enableService().setSuperClass(SuperService.class)
                .setOverwriteEnable(true)
        ;

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
        GeneratorFactory.registerGenerator("vo", new VoGenerator());

        return globalConfig;
    }

}