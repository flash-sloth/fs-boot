package top.fsfsfs.main.msg.controller;

import com.mybatisflex.codegen.config.ControllerConfig;
import io.github.linpeilie.Converter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.basic.mvcflex.controller.SuperController;
import top.fsfsfs.main.generator.entity.type.ControllerDesign;
import top.fsfsfs.main.generator.properties.CodeCreatorProperties;

@RestController
@RequestMapping("/msg/test")
@AllArgsConstructor
@Tag(name = "Msg测试接口")
@Slf4j
public class MsgTestController {
    private final static Converter CONVERTER = new Converter();
    @GetMapping("")
    public R<String> hello() {

        CodeCreatorProperties.ControllerRule controllerRule = new CodeCreatorProperties.ControllerRule();
        controllerRule.setClassPrefix("cc");
        controllerRule.setSuperClass(SuperController.class);
        controllerRule.setRestStyle(false);
        controllerRule.setPackageName("appppa");
        controllerRule.setRequestMappingPrefix("///xxx");
        controllerRule.setWithCrud(true);
        ControllerConfig controllerConfig = new ControllerConfig();
        CONVERTER.convert(controllerRule, controllerConfig);
        System.out.println(controllerConfig);

        log.info("hello fs");
        return R.success("okkk");
    }


}
