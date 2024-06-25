package top.fsfsfs.main.generator.mapping;

import com.mybatisflex.codegen.config.QueryConfig;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import top.fsfsfs.main.generator.entity.type.QueryDesign;
import top.fsfsfs.util.utils.BaseMapping;

/**
 *
 * @author tangyh
 * @since 2024/6/23 14:24
 */
@Mapper(componentModel = "spring")
public interface QueryDesignMapping extends BaseMapping<QueryDesign, QueryConfig> {
    QueryDesignMapping INSTANCE = Mappers.getMapper(QueryDesignMapping.class);
}
