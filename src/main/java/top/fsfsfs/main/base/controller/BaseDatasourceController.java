package top.fsfsfs.main.base.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.base.dto.BaseDatasourceDto;
import top.fsfsfs.main.base.entity.BaseDatasource;
import top.fsfsfs.main.base.query.BaseDatasourceQuery;
import top.fsfsfs.main.base.service.BaseDatasourceService;
import top.fsfsfs.main.base.vo.BaseDatasourceVo;

/**
 * 数据源 控制层。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@RestController
@Validated
@Tag(name = "数据源接口")
@RequestMapping("/baseDatasource")
public class BaseDatasourceController extends SuperController<BaseDatasourceService, Long, BaseDatasource, BaseDatasourceDto, BaseDatasourceQuery, BaseDatasourceVo> {
}
