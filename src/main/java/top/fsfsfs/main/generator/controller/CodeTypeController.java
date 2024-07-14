package top.fsfsfs.main.generator.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.generator.dto.CodeTypeDto;
import top.fsfsfs.main.generator.entity.CodeType;
import top.fsfsfs.main.generator.query.CodeTypeQuery;
import top.fsfsfs.main.generator.service.CodeTypeService;
import top.fsfsfs.main.generator.vo.CodeTypeVo;

/**
 * 字段类型管理 控制层。
 *
 * @author tangyh
 * @since 2024-07-14 11:57:37
 */
@RestController
@Validated
@Tag(name = "字段类型管理接口")
@RequestMapping("/main/generator/codeType")
public class CodeTypeController extends SuperController<CodeTypeService, Long, CodeType, CodeTypeDto, CodeTypeQuery, CodeTypeVo> {
}
