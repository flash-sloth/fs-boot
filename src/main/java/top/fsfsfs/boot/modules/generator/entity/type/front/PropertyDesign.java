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

package top.fsfsfs.boot.modules.generator.entity.type.front;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 代码生成器 实体类属性配置
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
public class PropertyDesign {
    /** JAVA字段名  */
    private String property;
    /** JAVA字段类型 */
    private String propertyType;
    /** TS字段类型 */
    private String tsType;
    /** 文档描述 */
    private String swaggerDescription;
    /** 主键 */
    private Boolean pk;
    /** 自增 */
    private Boolean increment;
    /** 非空 */
    private Boolean notNull;
    /** 逻辑删除 */
    private Boolean logicDelete;
    /** 乐观锁  */
    private Boolean version;
    /** 是否是大字段 */
    private Boolean large;
    /** 是否是租户ID */
    private Boolean tenant;

}