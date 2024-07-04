package top.fsfsfs.main.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.generator.dto.CodeCreatorContentDto;
import top.fsfsfs.main.generator.entity.CodeCreatorContent;
import top.fsfsfs.main.generator.query.CodeCreatorContentQuery;
import top.fsfsfs.main.generator.service.CodeCreatorContentService;
import top.fsfsfs.main.generator.vo.CodeCreatorContentVo;

/**
 * 代码生成内容 控制层。
 *
 * @author tangyh
 * @since 2024-06-27
 */
@RestController
@Validated
@Tag(name = "代码生成内容接口")
@RequestMapping("/main/codeCreatorContent")
public class CodeCreatorContentController extends SuperController<CodeCreatorContentService, Long, CodeCreatorContent, CodeCreatorContentDto, CodeCreatorContentQuery, CodeCreatorContentVo> {
}
