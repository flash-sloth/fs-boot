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

package top.fsfsfs.main.generator.entity.type.front;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.fsfsfs.codegen.config.FrontConfig;

/**
 * 代码生成器 前端配置
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
@Builder
public class FrontDesign {

    /**
     * 代码生成目录。
     */
    @NotEmpty(message = "请填写前端代码生成目录")
    private String sourceDir;
    /**
     * 表单打开方式。
     */
    @NotNull(message = "请填写表单打开方式")
    private FrontConfig.OpenMode openMode;

    /**
     * 布局方式。
     */
    @NotNull(message = "请填写布局方式")
    private FrontConfig.Layout layout;

    /**
     * 是否启用国际化。
     */
    @NotNull(message = "请填写国际化")
    private Boolean i18n;
    /**
     * 页面缓存。
     */
    @NotNull(message = "请填写页面缓存")
    private Boolean keepAlive;


}
