package top.fsfsfs.main.generator.controller;

import cn.hutool.core.lang.tree.Tree;
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
import top.fsfsfs.main.generator.dto.CodeCreatorDto;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.query.CodeCreatorQuery;
import top.fsfsfs.main.generator.service.CodeCreatorService;
import top.fsfsfs.main.generator.vo.CodeCreatorVo;
import top.fsfsfs.main.generator.dto.TableImportDto;

import java.util.List;

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

    @Operation(summary = "批量预览", description = "批量预览")
    @PostMapping("/preview")
    @WebLog(value = "批量预览")
    public R<List<Tree<Long>>> preview(@RequestBody List<Long> ids) {
        return R.success(superService.preview(ids));
    }
//
//    @Operation(summary = "批量生成代码", description = "批量生成代码")
//    @PostMapping("/generator")
//    @WebLog(value = "批量生成代码")
//    public R<Boolean> generator(@RequestBody @Validated DefGenVO defGenVO) {
//        superService.generator(defGenVO);
//        return R.success(true);
//    }
//
//    @Operation(summary = "批量下载代码", description = "批量下载代码")
//    @GetMapping(value = "/download", produces = "application/octet-stream")
//    @WebLog(value = "批量下载代码")
//    public void download(HttpServletResponse response, @RequestParam List<Long> ids, @RequestParam TemplateEnum template) {
//        DownloadVO download = superService.download(ids, template);
//        write(download.getData(), download.getFileName(), response);
//    }
}