package top.fsfsfs.demo.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.demo.test.dto.CodeTestSimpleDto;
import top.fsfsfs.demo.test.entity.CodeTestSimple;
import top.fsfsfs.demo.test.query.CodeTestSimpleQuery;
import top.fsfsfs.demo.test.service.CodeTestSimpleService;
import top.fsfsfs.demo.test.vo.CodeTestSimpleVo;

/**
 * 单表标准字段示例表 控制层。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@RestController
@Validated
@Tag(name = "单表标准字段示例表接口")
@RequestMapping("/demo/codeTestSimple")
public class CodeTestSimpleController extends SuperController<CodeTestSimpleService, Long, CodeTestSimple, CodeTestSimpleDto, CodeTestSimpleQuery, CodeTestSimpleVo> {
}
