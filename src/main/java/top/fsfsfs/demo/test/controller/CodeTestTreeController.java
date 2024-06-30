package top.fsfsfs.demo.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.demo.test.dto.CodeTestTreeDto;
import top.fsfsfs.demo.test.entity.CodeTestTree;
import top.fsfsfs.demo.test.query.CodeTestTreeQuery;
import top.fsfsfs.demo.test.service.CodeTestTreeService;
import top.fsfsfs.demo.test.vo.CodeTestTreeVo;

/**
 * 树结构标准字段示例表 控制层。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@RestController
@Validated
@Tag(name = "树结构标准字段示例表接口")
@RequestMapping("/demo/codeTestTree")
public class CodeTestTreeController extends SuperTreeController<CodeTestTreeService, Long, CodeTestTree, CodeTestTreeDto, CodeTestTreeQuery, CodeTestTreeVo> {
}
