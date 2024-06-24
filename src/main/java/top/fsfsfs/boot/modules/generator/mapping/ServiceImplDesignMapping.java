package top.fsfsfs.boot.modules.generator.mapping;

import com.mybatisflex.codegen.config.ServiceImplConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.boot.modules.generator.entity.type.ServiceImplDesign;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface ServiceImplDesignMapping extends BaseMapping<ServiceImplDesign, ServiceImplConfig> {
    ServiceImplDesignMapping INSTANCE = Mappers.getMapper(ServiceImplDesignMapping.class);
    /**
     * A更新B
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    void copySourceProperties(CodeCreatorProperties.ServiceImplRule source, @MappingTarget ServiceImplConfig target);

}
