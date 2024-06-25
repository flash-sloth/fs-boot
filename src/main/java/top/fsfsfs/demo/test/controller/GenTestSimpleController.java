package top.fsfsfs.demo.test.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.demo.test.dto.GenTestSimpleDto;
import top.fsfsfs.demo.test.entity.GenTestSimple;
import top.fsfsfs.demo.test.query.GenTestSimpleQuery;
import top.fsfsfs.demo.test.service.GenTestSimpleService;
import top.fsfsfs.demo.test.vo.GenTestSimpleVo;

/**
 * 测试单表结构 控制层。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@RestController
@Validated
@Tag(name = "测试单表结构接口")
@RequestMapping("/demo/genTestSimple")
public class GenTestSimpleController extends SuperController<GenTestSimpleService, Long, GenTestSimple, GenTestSimpleDto, GenTestSimpleQuery, GenTestSimpleVo> {
}
