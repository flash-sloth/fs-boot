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
import com.mybatisflex.codegen.entity.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.DtoRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.EntityRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.QueryRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.StrategyRule;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties.VoRule;

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
    private DataSource dataSource;
    private CodeCreatorProperties codeCreatorProperties;

    public GeneratorUtil(CodeCreatorProperties codeCreatorProperties) {
        this.codeCreatorProperties = codeCreatorProperties;
    }

    public void generate() {
//        generate(getTables());
    }

    public List<Table> getTables(Set<String> tableNames) {
        StrategyRule strategyRule = codeCreatorProperties.getStrategyRule();
        EntityRule entityRule = codeCreatorProperties.getEntityRule();
        QueryRule queryRule = codeCreatorProperties.getQueryRule();
        VoRule voRule = codeCreatorProperties.getVoRule();
        DtoRule dtoRule = codeCreatorProperties.getDtoRule();

        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setJdkVersion(17);

        globalConfig.getJavadocConfig()
                .setTableCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setTableSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
                .setColumnCommentFormat(comment -> StrUtil.replace(comment, "\n", "\n     * "))
                .setColumnSwaggerCommentFormat(comment -> StrUtil.replace(StrUtil.replace(comment, "\n", " "), "\"", "\\\""))
//                .setAuthor(javadocRule.getAuthor())
        ;

        //TODO 这里有问题
//        PackageConfig packageConfig = globalConfig.getPackageConfig();
//        BeanUtil.copyProperties(packageRule, packageConfig);

        globalConfig.getStrategyConfig()
                .setTablePrefix(strategyRule.getTablePrefix())
                .setGenerateTables(tableNames);

//        globalConfig.setTableConfig(TableConfig.create()
//                .setInsertListenerClass(DefaultInsertListener.class)
//                .setUpdateListenerClass(DefaultUpdateListener.class));

        globalConfig.enableEntity().setClassPrefix(entityRule.getClassPrefix()).setClassSuffix(entityRule.getClassSuffix());
//        .setSuperClass(SuperEntity.class).setGenericityType(Long.class).setWithLombok(true).setWithBaseClassEnable(true).setOverwriteEnable(true)


        globalConfig.enableVo().setClassPrefix(voRule.getClassPrefix()).setClassSuffix(voRule.getClassSuffix());
//                .setWithLombok(true).setWithSwagger(true).setWithExcel(true).setOverwriteEnable(true)


        globalConfig.enableQuery().setClassPrefix(queryRule.getClassPrefix()).setClassSuffix(queryRule.getClassSuffix());
//                .setWithLombok(true).setWithSwagger(true).setWithExcel(true).setOverwriteEnable(true)


        globalConfig.enableDto().setClassPrefix(dtoRule.getClassPrefix()).setClassSuffix(dtoRule.getClassSuffix());
//                .setWithLombok(true).setWithSwagger(true).setWithValidator(true)
//                .setOverwriteEnable(true)
//                .setIgnoreColumns(SuperEntity.CREATED_AT_FIELD, SuperEntity.CREATED_BY_FIELD,
//                        SuperEntity.UPDATED_AT_FIELD, SuperEntity.UPDATED_BY_FIELD,
//                        SuperEntity.DELETED_AT_FIELD, SuperEntity.DELETED_BY_FIELD);

//        globalConfig.enableController().setSuperClass(SuperController.class).setOverwriteEnable(true);
//        globalConfig.enableMapper().setSuperClass(SuperMapper.class).setOverwriteEnable(true);
//        globalConfig.enableService().setSuperClass(SuperService.class).setOverwriteEnable(true);
//        globalConfig.enableServiceImpl().setSuperClass(SuperServiceImpl.class).setOverwriteEnable(true);

        Generator generator = new Generator(dataSource, globalConfig);
        return generator.getTables();
    }
}
