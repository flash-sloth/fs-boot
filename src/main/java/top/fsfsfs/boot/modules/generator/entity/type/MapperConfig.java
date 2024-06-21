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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import com.mybatisflex.codegen.constant.GenerationStrategyEnum;

import java.util.List;

/**
 * 代码生成器 Mapper类配置
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
public class MapperConfig {
    /** 包名 */
    private String packageName = "mapper";
    /**
     * 类的后缀。
     */
    private String classSuffix = "Mapper";
    /**
     * 父类完整类名
     */
    private String superClassName = SuperMapper.class.getName();
    /**
     * 生成策略
     */
    private GenerationStrategyEnum generationStrategy = GenerationStrategyEnum.EXIST_IGNORE;

    /** 导入的包 */
    private List<String> importPackageList;
}
