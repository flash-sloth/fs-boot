package top.fsfsfs.boot.modules.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.annotation.log.WebLog;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.boot.modules.generator.dto.CodeCreatorDto;
import top.fsfsfs.boot.modules.generator.entity.CodeCreator;
import top.fsfsfs.boot.modules.generator.query.CodeCreatorQuery;
import top.fsfsfs.boot.modules.generator.service.CodeCreatorService;
import top.fsfsfs.boot.modules.generator.vo.CodeCreatorVo;
import top.fsfsfs.boot.modules.generator.vo.TableImportDto;

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
    @Operation(summary = "导入表结构", description = "导入表结构")
    @PostMapping(value = "/importTable")
    @WebLog(value = "'导入表结构", response = false)
    public R<Boolean> importTable(@RequestBody @Validated TableImportDto importDto) {
        return R.success(superService.importTable(importDto));
    }
}
