package top.fsfsfs.main.test.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.test.entity.DefGenTestTree;
import top.fsfsfs.main.test.service.DefGenTestTreeService;
import top.fsfsfs.main.test.vo.DefGenTestTreeQueryVO;
import top.fsfsfs.main.test.vo.DefGenTestTreeResultVO;
import top.fsfsfs.main.test.vo.DefGenTestTreeVO;


@RestController
@RequestMapping("/system/cache")
@RequiredArgsConstructor
@Tag(name = "tree含缓存CRUD")
@Slf4j
public class DefGenTestTreeCacheController extends SuperController<DefGenTestTreeService, Long,
        DefGenTestTree, DefGenTestTreeVO, DefGenTestTreeQueryVO, DefGenTestTreeResultVO> {


}
