package top.fsfsfs.boot.modules.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.boot.modules.system.entity.DefGenTestSimple;
import top.fsfsfs.boot.modules.system.service.TestSimpleService;

@RestController
@RequestMapping("/system/test")
@AllArgsConstructor
@Tag(name = "测试接口")
@Slf4j
public class TestController {

    private final TestSimpleService testSimpleService;

    @GetMapping("")
    public R<String> hello() {
        log.info("hello fs");
        return R.success("okkk");
    }

    @GetMapping("/list")
    public R<Object> list() {
        return R.success(testSimpleService.list());
    }

    @PostMapping("/save")
    public R<Object> save(@RequestBody DefGenTestSimple simple) {
        return R.success(testSimpleService.save(simple));
    }

    @PostMapping("/updateById")
    public R<Object> updateById(@RequestBody DefGenTestSimple simple) {
        return R.success(testSimpleService.updateById(simple));
    }
}
