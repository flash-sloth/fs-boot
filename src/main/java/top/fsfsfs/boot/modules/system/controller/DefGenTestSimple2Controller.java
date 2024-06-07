package top.fsfsfs.boot.modules.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple2;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimpleService;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2QueryVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2ResultVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2VO;

@RestController
@RequestMapping("/system/super")
@AllArgsConstructor
@Tag(name = "super接口")
@Slf4j
public class DefGenTestSimple2Controller extends SuperController<DefGenTestSimpleService, Long,
        DefGenTestSimple2, DefGenTestSimple2VO, DefGenTestSimple2QueryVO, DefGenTestSimple2ResultVO> {


}
