package top.fsfsfs.main.generator.controller;

import com.mybatisflex.core.query.QueryWrapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.utils.ControllerUtil;
import top.fsfsfs.main.generator.dto.CodeBaseClassDto;
import top.fsfsfs.main.generator.entity.CodeBaseClass;
import top.fsfsfs.main.generator.query.CodeBaseClassQuery;
import top.fsfsfs.main.generator.service.CodeBaseClassService;
import top.fsfsfs.main.generator.vo.CodeBaseClassVo;
import top.fsfsfs.util.utils.BeanPlusUtil;

import java.util.List;

/**
 * 基类管理 控制层。
 *
 * @author tangyh
 * @since 2024-07-01
 */
@RestController
@Validated
@Tag(name = "基类管理接口")
@RequestMapping("/main/codeBaseClass")
public class CodeBaseClassController extends SuperController<CodeBaseClassService, Long, CodeBaseClass, CodeBaseClassDto, CodeBaseClassQuery, CodeBaseClassVo> {
    @Override
    public R<List<CodeBaseClassVo>> list(CodeBaseClassQuery data) {
        CodeBaseClass entity = BeanPlusUtil.toBean(data, getEntityClass());
        QueryWrapper wrapper = QueryWrapper.create(entity, ControllerUtil.buildOperators(entity.getClass()))
                .orderBy(CodeBaseClass::getWeight, true);
        List<CodeBaseClass> list = getSuperService().list(wrapper);
        return success(BeanPlusUtil.toBeanList(list, getVoClass()));
    }
}
