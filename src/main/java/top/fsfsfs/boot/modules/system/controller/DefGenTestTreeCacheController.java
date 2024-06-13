package top.fsfsfs.boot.modules.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperCacheController;
import top.fsfsfs.boot.modules.system.entity.DefGenTestTree;
import top.fsfsfs.boot.modules.system.service.DefGenTestTreeService;
import top.fsfsfs.boot.modules.system.vo.DefGenTestTreeQueryVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestTreeResultVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestTreeVO;


@RestController
@RequestMapping("/system/cache")
@RequiredArgsConstructor
@Tag(name = "tree含缓存CRUD")
@Slf4j
public class DefGenTestTreeCacheController extends SuperCacheController<DefGenTestTreeService, Long,
        DefGenTestTree, DefGenTestTreeVO, DefGenTestTreeQueryVO, DefGenTestTreeResultVO> {


}
