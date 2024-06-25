package top.fsfsfs.main.test.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fsfsfs.main.test.entity.DefGenTestSimple;
import top.fsfsfs.main.test.mapper.DefGenTestSimpleMapper;
import top.fsfsfs.main.test.service.TestSimpleService;

/**
 * <p>
 * 系统授权表 服务实现类
 * </p>
 *
 * @author sz
 * @since 2024-01-22
 */
@Service
@RequiredArgsConstructor
public class TestSimpleServiceImpl extends ServiceImpl<DefGenTestSimpleMapper, DefGenTestSimple> implements TestSimpleService {


}