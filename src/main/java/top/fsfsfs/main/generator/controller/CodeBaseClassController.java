package top.fsfsfs.main.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.generator.dto.CodeBaseClassDto;
import top.fsfsfs.main.generator.entity.CodeBaseClass;
import top.fsfsfs.main.generator.query.CodeBaseClassQuery;
import top.fsfsfs.main.generator.service.CodeBaseClassService;
import top.fsfsfs.main.generator.vo.CodeBaseClassVo;

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
}
