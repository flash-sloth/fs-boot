package top.fsfsfs.boot;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.mybatisflex.core.table.TableInfo;
import com.mybatisflex.core.table.TableInfoFactory;
import io.github.linpeilie.Converter;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.basic.utils.StrPool;
import top.fsfsfs.codegen.Generator;
import top.fsfsfs.codegen.config.ColumnConfig;
import top.fsfsfs.codegen.config.EntityConfig;
import top.fsfsfs.codegen.config.GlobalConfig;
import top.fsfsfs.codegen.config.TableConfig;
import top.fsfsfs.codegen.constant.GenerationStrategyEnum;
import top.fsfsfs.codegen.dialect.JdbcTypeMapping;
import top.fsfsfs.codegen.entity.Column;
import top.fsfsfs.codegen.entity.Table;
import top.fsfsfs.main.generator.entity.type.EntityDesign;
import top.fsfsfs.main.system.entity.SysMenu;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CodeGeneratorTest {

    @Test
    public void test1() {
        TableInfo tableInfo = TableInfoFactory.ofEntityClass(SysMenu.class);
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

    @Test
    public void zipTest() throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        String zipOutputFile = "/Users/tangyh/Downloads/src/main/java/com/xxx/test.java";
        zip.putNextEntry(new ZipEntry(zipOutputFile));
        IOUtils.write("package sss;", zip, StrPool.UTF8);
        zip.closeEntry();
        IoUtil.flush(zip);

        zipOutputFile = "/Users/tangyh/Downloads/src/main/java/com/ddd/aaaa.java";
        zip.putNextEntry(new ZipEntry(zipOutputFile));
        IOUtils.write("package aaaa;", zip, StrPool.UTF8);
        zip.closeEntry();
        IoUtil.flush(zip);

        IoUtil.close(zip);
        Files.write(Paths.get("/Users/tangyh/Downloads/z2.zip"), outputStream.toByteArray());
    }

    @Test
    public void hutoolZip() throws Exception {
        File file = new File("/Users/tangyh/Downloads", "汤云汉测试.pdf");
        System.out.println(file.exists());
    }


    @Test
    public void test4() {
        String javaDirKey = "src\\main\\java.123";
        System.out.println(Paths.get(javaDirKey, "aaa", "ddd").toString());
    }

    private final static Converter CONVERTER = new Converter();

    @Test
    public void test5() {
        EntityDesign entityDesign = new EntityDesign();
        entityDesign.setName("aa");
        entityDesign.setSuperClassName(SuperEntity.class.getName());
        EntityConfig entityConfig = new EntityConfig();
        CONVERTER.convert(entityDesign, entityConfig);

        System.out.println(entityConfig);
    }

    public static void main(String[] args) {
        //配置数据源
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/flash_sloth?characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("root");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle1();

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();

    }

    public static GlobalConfig createGlobalConfigUseStyle1() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        //globalConfig.setBasePackage("com.fsfsfs.demo.test");
        globalConfig.setBasePackage("top.fsfsfs");
        globalConfig.getPackageConfig().setSubSystem("main").setModule("generator");

//        globalConfig.setEntityGenerateEnable();
        //设置表前缀和只生成哪些表
        globalConfig.setTablePrefix("fs_");
        globalConfig.setGenerateTable("fs_code_type");
//        globalConfig.setGenerateTable("fs_code_test_tree");
//        globalConfig.setGenerateTable("fs_code_test_simple");

//        globalConfig.setGenerateTable("fs_code_creator_column");

//        globalConfig.setGenerateTable("fs_code_creator", "fs_code_creator_column");
//        globalConfig.setGenerateTable("fs_sys_menu");
        globalConfig.setJdkVersion(17);
        globalConfig.getJavadocConfig()
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setTableCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setTableSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
        ;

        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        globalConfig.enableEntity()
//                .setSuperClass(TreeEntity.class)
                .setSuperClass(SuperEntity.class)
                .setGenericityType(Long.class)
                .setWithLombok(true)
                .setWithBaseClassEnable(true)
        ;

        globalConfig.enableVo()
//                .setSuperClass(TreeNode.class)
//                .setGenericityType(Long.class)
                .setWithLombok(true)
                .setWithSwagger(true)
                .setWithExcel(true)
//                .setImplInterfaces(Serializable.class)
        ;

        globalConfig.enableQuery()
                .setWithLombok(true).setWithSwagger(true).setWithExcel(true)
                .setImplInterfaces(Serializable.class);

//
        globalConfig.enableDto()
                .setWithLombok(true).setWithSwagger(true).setWithValidator(true)
                //.setImplInterfaces(Serializable.class)
                .setIgnoreColumns(new HashSet<>(Arrays.asList(SuperEntity.CREATED_AT_FIELD, SuperEntity.CREATED_BY_FIELD,
                        SuperEntity.UPDATED_AT_FIELD, SuperEntity.UPDATED_BY_FIELD,
                        SuperEntity.DELETED_AT_FIELD, SuperEntity.DELETED_BY_FIELD)));

        globalConfig.enableController()
                .setRequestMappingPrefix("/" + globalConfig.getPackageConfig().getSubSystem() + "/" + globalConfig.getPackageConfig().getModule())
//                .setWithCrud(true)
                .setSuperClass(SuperController.class)
//                .setSuperClass(SuperWriteController.class)
//                .setSuperClass(SuperReadController.class)
//               .setSuperClass(SuperTreeController.class)
//               .setSuperClass(SuperSimpleController.class)
                .setGenerationStrategy(GenerationStrategyEnum.OVERWRITE);

        globalConfig.enableMapperXml();

//        //设置生成 mapper
        globalConfig.enableMapper().setSuperClass(SuperMapper.class);
        globalConfig.enableService().setSuperClass(SuperService.class);
        globalConfig.enableServiceImpl().setSuperClass(SuperServiceImpl.class);
//        globalConfig.enableFront();

        //可以单独配置某个列
        ColumnConfig columnConfig = new ColumnConfig();
        columnConfig.setColumnName("tenant_id");
        columnConfig.setLarge(true);
        columnConfig.setVersion(true);
        globalConfig.setColumnConfig("fs_sys_param", columnConfig);

        JdbcTypeMapping.setTypeMapper(new JdbcTypeMapping.JdbcTypeMapper() {
            @Override
            public String getType(String rawType, String jdbcType, Table table, Column column) {

                return null;
            }
        });


        return globalConfig;
    }

}
