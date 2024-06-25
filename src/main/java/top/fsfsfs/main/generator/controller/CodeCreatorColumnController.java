package top.fsfsfs.main.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.generator.dto.CodeCreatorColumnDto;
import top.fsfsfs.main.generator.entity.CodeCreatorColumn;
import top.fsfsfs.main.generator.query.CodeCreatorColumnQuery;
import top.fsfsfs.main.generator.service.CodeCreatorColumnService;
import top.fsfsfs.main.generator.vo.CodeCreatorColumnVo;

/**
 * 代码生成字段 控制层。
 *
 * @author tangyh
 * @since 2024-06-21
 */
@RestController
@Validated
@Tag(name = "代码生成字段接口")
@RequestMapping("/codeCreatorColumn")
public class CodeCreatorColumnController extends SuperController<CodeCreatorColumnService, Long, CodeCreatorColumn, CodeCreatorColumnDto, CodeCreatorColumnQuery, CodeCreatorColumnVo> {
}
