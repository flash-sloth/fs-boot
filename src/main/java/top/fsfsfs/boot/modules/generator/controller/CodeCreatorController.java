package top.fsfsfs.boot.modules.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.boot.modules.generator.dto.CodeCreatorDto;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.query.CodeCreatorQuery;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorService;
import top.fsfsfs.boot.modules.generator.vo.CodeCreatorVo;

/**
 * 代码生成 控制层。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@RestController
@Validated
@Tag(name = "代码生成接口")
@RequestMapping("/codeCreator")
public class CodeCreatorController extends SuperController<CodeCreatorService, Long, CodeCreator, CodeCreatorDto, CodeCreatorQuery, CodeCreatorVo> {
}
