package top.fsfsfs.main.generator.mapping;

import com.mybatisflex.codegen.config.ControllerConfig;
import com.mybatisflex.codegen.config.ServiceConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.main.generator.entity.type.ControllerDesign;
import top.fsfsfs.main.generator.entity.type.ServiceDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface ServiceDesignMapping extends BaseMapping<ServiceDesign, ServiceConfig> {
    ServiceDesignMapping INSTANCE = Mappers.getMapper(ServiceDesignMapping.class);
    /**
     * A更新B
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    void copySourceProperties(CodeCreatorProperties.ServiceRule source, @MappingTarget ServiceConfig target);

}
