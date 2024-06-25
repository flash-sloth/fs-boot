package top.fsfsfs.main.oss.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;

@RestController
@RequestMapping("/oss/test")
@AllArgsConstructor
@Tag(name = "Oss测试接口")
@Slf4j
public class OssTestController {

    @GetMapping("")
    @Operation(summary = "占位接口")
    public R<String> hello() {
        log.info("hello fs");
        return R.success("okkk");
    }
}
