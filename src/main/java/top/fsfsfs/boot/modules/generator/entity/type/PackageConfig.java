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

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 代码生成器 包信息
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
public class PackageConfig {
    /**
     * 代码生成目录。
     */
    @NotEmpty(message = "请填写后端代码生成目录")
    private String sourceDir;
    /**
     * 根包名
     */
    @NotEmpty(message = "请填写模块根包名")
    private String basePackage;
    /** 模块包名 */
    @NotEmpty(message = "请填写模块包名")
    private String module;

    /** 模块包 描述 */
    @NotEmpty(message = "请填写模块包描述")
    private String moduleDescription;
    /** 子系统Id */
    @NotNull(message = "请选择子系统")
    private Long subSystemId;

    private String author;

}
