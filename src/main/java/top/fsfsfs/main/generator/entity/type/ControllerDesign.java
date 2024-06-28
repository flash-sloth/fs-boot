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

package top.fsfsfs.main.generator.entity.type;

import top.fsfsfs.codegen.config.ControllerConfig;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 代码生成器 Controller配置
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
@AutoMapper(target = ControllerConfig.class)
public class ControllerDesign {
    /** 包名 */
    private String packageName;
    /**
     * 类的前缀。
     */
    private String classPrefix;

    /**
     * 类的后缀。
     */
    private String classSuffix;
    /**
     *  类上面 RequestMapping注解，访问路径的前缀。
     */
    private String requestMappingPrefix;

    /** 在Controller中生成 CRUD 方法 */
    private Boolean withCrud;
    /**
     * 生成 REST 风格的 Controller。
     */
    private Boolean restStyle;

    /**
     * 父类完整类名
     */
    private String superClassName;
}
