/*
 * Copyright (c) 2024.  flash-sloth (244387066@qq.com).
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.fsfsfs.boot.modules.generator;

import cn.hutool.core.util.StrUtil;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.TableConfig;
import com.mybatisflex.codegen.entity.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 代码生成
 * @author tangyh
 * @since 2024/6/21 12:46
 */
@Slf4j
@AllArgsConstructor
public class GeneratorUtil {
    protected DataSource dataSource;

    public void generate() {
//        generate(getTables());
    }

    public void importTables(Set<String> tableNames) {

        List<Table> tables = getTables(tableNames);

        List<CodeCreator> list = new ArrayList<>();
        for (Table table : tables) {
            CodeCreator codeCreator = new CodeCreator();

            codeCreator.setTableName(table.getName());
            codeCreator.setTableDescription(table.getComment());


            list.add(codeCreator);
        }
    }

    public List<Table> getTables(Set<String> tableNames) {

//        for (String tableName : tableNames) {
//            Table tableMeta = MetaUtil.getTableMeta(dataSource, tableName);
//            tableMeta.getPkNames();
//
//            CodeCreator table = new CodeCreator();
//            table.setTableName(tableMeta.getTableName());
//            table.setTableDescription(tableMeta.getComment());
//            list.add(table);
//        }

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBasePackage("top.fsfsfs.boot.modules.test");

        globalConfig.setTablePrefix("fs_");
//        globalConfig.setGenerateTables(tableNames);
        globalConfig.setGenerateTable("fs_gen_test_simple");

        globalConfig.setJdkVersion(17);
        globalConfig.getJavadocConfig()
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""));
        globalConfig.setTableConfig(TableConfig.create()
                .setInsertListenerClass(DefaultInsertListener.class)
                .setUpdateListenerClass(DefaultUpdateListener.class));

        globalConfig.enableEntity().setSuperClass(SuperEntity.class)
                .setGenericityType(Long.class).setOverwriteEnable(true)
                .setWithLombok(true)
                .setWithBaseClassEnable(true)
        ;

        globalConfig.enableVo()
                .setWithLombok(true).setWithSwagger(true).setWithExcel(true)
                .setOverwriteEnable(true);

        globalConfig.enableQuery()
                .setWithLombok(true).setWithSwagger(true).setWithExcel(true)
                .setOverwriteEnable(true);

        globalConfig.enableDto()
                .setWithLombok(true).setWithSwagger(true).setWithValidator(true)
                .setOverwriteEnable(true)
                .setIgnoreColumns(SuperEntity.CREATED_AT_FIELD, SuperEntity.CREATED_BY_FIELD,
                        SuperEntity.UPDATED_AT_FIELD, SuperEntity.UPDATED_BY_FIELD,
                        SuperEntity.DELETED_AT_FIELD, SuperEntity.DELETED_BY_FIELD)
        ;

        globalConfig.enableController().setSuperClass(SuperController.class).setOverwriteEnable(true);
        globalConfig.enableMapper().setSuperClass(SuperMapper.class).setOverwriteEnable(true);
        globalConfig.enableService().setSuperClass(SuperService.class).setOverwriteEnable(true);
        globalConfig.enableServiceImpl().setSuperClass(SuperServiceImpl.class).setOverwriteEnable(true);

        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);
        return generator.getTables();
    }
}
