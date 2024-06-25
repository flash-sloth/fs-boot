package top.fsfsfs.boot.modules.generator.mapping;

import com.mybatisflex.codegen.config.FrontConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.boot.modules.generator.entity.type.front.FrontDesign;
import top.fsfsfs.boot.modules.generator.properties.CodeCreatorProperties;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface FrontDesignMapping extends BaseMapping<FrontDesign, FrontConfig> {
    FrontDesignMapping INSTANCE = Mappers.getMapper(FrontDesignMapping.class);

    /**
     * A更新B
     *
     * @param source 原始对象
     * @param target 目标对象
     */
    void copySourceProperties(CodeCreatorProperties.FrontRule source, @MappingTarget FrontDesign target);

}
