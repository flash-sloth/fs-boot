package top.fsfsfs.config;

import io.github.linpeilie.annotations.ComponentModelConfig;
import org.mapstruct.Named;
import top.fsfsfs.util.utils.ClassUtils;

/**
 * 类型转换
 * @author tangyh
 * @since 2024/6/28 21:31
 */

@ComponentModelConfig(componentModel = "default")
public class MapStructConfiguration {
    public static final String NAME_TO_CLASS = "nameToClass";

    @Named(NAME_TO_CLASS)
    public Class<?> nameToClass(String name) {
        return ClassUtils.forName(name);
    }

}
