package top.fsfsfs.boot.modules.system.controller;

import com.mybatisflex.core.constant.SqlOperator;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.query.SqlOperators;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.base.entity.SuperEntity;
import top.fsfsfs.basic.mvcflex.controller.SuperCacheController;
import top.fsfsfs.basic.mvcflex.request.PageFlexUtil;
import top.fsfsfs.basic.mvcflex.request.PageParams;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple2;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimple3Service;
import top.fsfsfs.boot.modules.system.service.DefGenTestSimpleService;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2QueryVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2ResultVO;
import top.fsfsfs.boot.modules.system.vo.DefGenTestSimple2VO;

import java.util.List;

import static top.fsfsfs.boot.modules.system.entity.table.DefGenTestSimple2TableDef.DEF_GEN_TEST_SIMPLE2;

@RestController
@RequestMapping("/system/cache")
@AllArgsConstructor
@Tag(name = "cache接口")
@Slf4j
public class DefGenTestSimple3Controller extends SuperCacheController<DefGenTestSimple3Service, Long,
        DefGenTestSimple2, DefGenTestSimple2VO, DefGenTestSimple2QueryVO, DefGenTestSimple2ResultVO> {


}
