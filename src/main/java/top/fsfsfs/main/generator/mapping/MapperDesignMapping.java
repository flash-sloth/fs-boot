package top.fsfsfs.main.generator.mapping;

import com.mybatisflex.codegen.config.MapperConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.main.generator.entity.type.MapperDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface MapperDesignMapping extends BaseMapping<MapperDesign, MapperConfig> {
    MapperDesignMapping INSTANCE = Mappers.getMapper(MapperDesignMapping.class);

    /**
     * A更新B
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    void copySourceProperties(CodeCreatorProperties.MapperRule source, @MappingTarget MapperConfig target);

}
