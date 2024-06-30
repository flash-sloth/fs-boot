package top.fsfsfs.main.generator.controller;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.annotation.log.WebLog;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.BaseEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.basic.mvcflex.request.DownloadVO;
import top.fsfsfs.main.generator.dto.CodeCreatorDto;
import top.fsfsfs.main.generator.dto.CodeGenDto;
import top.fsfsfs.main.generator.dto.TableImportDto;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.query.CodeCreatorQuery;
import top.fsfsfs.main.generator.service.CodeCreatorService;
import top.fsfsfs.main.generator.vo.CodeCreatorVo;

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
@RequestMapping("/main/codeCreator")
public class CodeCreatorController extends SuperController<CodeCreatorService, Long, CodeCreator, CodeCreatorDto, CodeCreatorQuery, CodeCreatorVo> {

    @Operation(summary = "查询指定数据源的表结构", description = "查询指定数据源的表结构")
    @PostMapping("/listTableMetadata")
    @WebLog(value = "查询指定数据源的表结构")
    public R<List<CodeCreatorVo>> listTableMetadata(@Parameter(description = "数据源ID") @RequestParam Long dsId) {
        return R.success(superService.listTableMetadata(dsId));
    }

    @Operation(summary = "导入表结构", description = "导入表结构")
    @PostMapping(value = "/importTable")
    @WebLog(value = "'导入表结构", response = false)
    public R<Boolean> importTable(@RequestBody @Validated TableImportDto importDto) {
        return R.success(superService.importTable(importDto));
    }

    @Operation(summary = "批量预览", description = "批量预览")
    @PostMapping("/preview")
    @WebLog(value = "批量预览")
    public R<List<Tree<Long>>> preview(@RequestBody CodeGenDto genDto) {
        return R.success(superService.preview(genDto));
    }

    @Operation(summary = "批量生成代码", description = "批量生成代码")
    @PostMapping("/generator")
    @WebLog(value = "批量生成代码")
    public R<Boolean> generator(@RequestBody @Validated(BaseEntity.Update.class) CodeGenDto genDto) {
        superService.generator(genDto);
        return R.success(true);
    }

    @Operation(summary = "批量下载代码", description = "批量下载代码")
    @GetMapping(value = "/download", produces = "application/octet-stream")
    @WebLog(value = "批量下载代码")
    public void download(HttpServletResponse response,
                         @Parameter(description = "表ID")
                         @RequestParam List<Long> ids,
                         @Parameter(description = "是否是使用配置信息，重新生成")
                         @RequestParam(required = false) Boolean reload) {
        DownloadVO download = superService.download(ids, reload);
        write(download.getData(), download.getFileName(), response);
    }
}
