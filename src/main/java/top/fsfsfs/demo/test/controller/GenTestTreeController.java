package top.fsfsfs.demo.test.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperTreeController;
import top.fsfsfs.demo.test.dto.GenTestTreeDto;
import top.fsfsfs.demo.test.entity.GenTestTree;
import top.fsfsfs.demo.test.query.GenTestTreeQuery;
import top.fsfsfs.demo.test.service.GenTestTreeService;
import top.fsfsfs.demo.test.vo.GenTestTreeVo;

/**
 * 测试树结构 控制层。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@RestController
@Validated
@Tag(name = "测试树结构接口")
@RequestMapping("/demo/genTestTree")
public class GenTestTreeController extends SuperTreeController<GenTestTreeService, Long, GenTestTree, GenTestTreeDto, GenTestTreeQuery, GenTestTreeVo> {
}
