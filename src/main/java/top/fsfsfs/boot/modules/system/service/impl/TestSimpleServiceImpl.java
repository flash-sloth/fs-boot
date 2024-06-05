package top.fsfsfs.boot.modules.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple;
import top.fsfsfs.boot.modules.system.mapper.DefGenTestSimpleMapper;
import top.fsfsfs.boot.modules.system.service.TestSimpleService;

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