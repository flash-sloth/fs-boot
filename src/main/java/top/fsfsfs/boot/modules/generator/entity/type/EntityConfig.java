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

package top.fsfsfs.boot.modules.generator.entity.type;

import com.mybatisflex.codegen.constant.GenerationStrategyEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 代码生成器 实体类配置
 *
 * @author tangyh
 * @since 2021-08-01 16:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class EntityConfig {
    /** 包名 */
    private String packageName;
    /**
     * 实体类名
     */
    private String name;
    /**
     * swagger注释
     */
    private String description;
    /**
     * Entity 类的父类完整类名
     */
    private String superClassName;
    /**
     * 生成策略
     */
    private GenerationStrategyEnum generationStrategy;

    /**
     * 是否总是生成 @Column 注解。
     */
    private Boolean alwaysGenColumnAnnotation;


    /** 导入的包 */
//    private List<String> importPackageList;
}
