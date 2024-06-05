package top.fsfsfs.boot.modules.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;

@RestController
@RequestMapping("/system/test")
@AllArgsConstructor
@Tag(name = "测试接口")
@Slf4j
public class TestController {

    @GetMapping("")
    public R<String> hello() {
        log.info("hello fs");
        return R.success("okkk");
    }
}
