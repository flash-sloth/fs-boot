package top.fsfsfs.demo.test.mapper;

import org.springframework.stereotype.Repository;
import top.fsfsfs.basic.mvcflex.mapper.SuperMapper;
import top.fsfsfs.demo.test.entity.GenTestSimple;

/**
 * 测试单表结构 映射层。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@Repository
public interface GenTestSimpleMapper extends SuperMapper<GenTestSimple> {

}