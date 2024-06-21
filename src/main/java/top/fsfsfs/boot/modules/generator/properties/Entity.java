package top.fsfsfs.boot.modules.generator.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import top.fsfsfs.basic.base.entity.SuperEntity;
import com.mybatisflex.codegen.constant.GenerationStrategyEnum;

import java.io.Serializable;

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
public class Entity {
    /** 包名 */
    private String packageName = "entity";
    /**
     * Entity 类的前缀。
     */
    private String classPrefix = "";

    /**
     * Entity 类的后缀。
     */
    private String classSuffix = "";
    /**
     * Entity 类的父类，可以自定义一些 BaseEntity 类。
     */
    private Class<?> superClass = SuperEntity.class;
    /** 父类泛型的类型 */
    private Class<?> genericityType = Long.class;
    /**
     * Entity 默认实现的接口。
     */
    private Class<?>[] implInterfaces = {Serializable.class};
    /**
     * 生成策略
     */
    private GenerationStrategyEnum generationStrategy = GenerationStrategyEnum.OVERWRITE;

    /**
     * Entity 是否使用 Lombok 注解。
     */
    private Boolean withLombok = true;

    /**
     * Entity 是否使用 Swagger 注解。
     */
    private Boolean withSwagger = false;
    /**
     * 是否总是生成 @Column 注解。
     */
    private Boolean alwaysGenColumnAnnotation = false;

    /** 导入的包 */
//    private List<String> importPackageList;
}
