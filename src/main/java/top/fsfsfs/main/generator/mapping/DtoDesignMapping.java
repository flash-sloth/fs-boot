package top.fsfsfs.main.generator.mapping;

import com.mybatisflex.codegen.config.DtoConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.main.generator.entity.type.DtoDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface DtoDesignMapping extends BaseMapping<DtoDesign, DtoConfig> {
    DtoDesignMapping INSTANCE = Mappers.getMapper(DtoDesignMapping.class);

    /**
     * A更新B
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    void copySourceProperties(CodeCreatorProperties.DtoRule source, @MappingTarget DtoDesign target);

}
