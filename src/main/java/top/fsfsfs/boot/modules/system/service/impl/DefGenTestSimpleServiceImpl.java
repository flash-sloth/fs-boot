package top.fsfsfs.boot.modules.system.service.impl;

import cn.hutool.core.util.TypeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import top.fsfsfs.basic.mvcflex.service.impl.SuperServiceImpl;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple2;
import top.fsfsfs.boot.modules.system.mapper.DefGenTestSimple2Mapper;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimpleService;

import java.lang.reflect.Type;

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
public class DefGenTestSimpleServiceImpl extends SuperServiceImpl<DefGenTestSimple2Mapper, DefGenTestSimple2> implements DefGenTestSimpleService {
    @Override
    public Class<DefGenTestSimple2> getEntityClass() {
        return DefGenTestSimple2.class;
    }

    public static void main(String[] args) {
        Type typeArgument = TypeUtil.getTypeArgument(DefGenTestSimpleServiceImpl.class, 1);

        System.out.println(typeArgument.getClass());

    }
}