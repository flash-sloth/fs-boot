package top.fsfsfs.boot.modules.auth.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.fsfsfs.basic.base.R;
import top.fsfsfs.boot.modules.auth.dto.LoginDto;
import top.fsfsfs.boot.modules.auth.vo.LoginVo;
import top.fsfsfs.boot.modules.auth.vo.UserInfoVo;

import java.util.ArrayList;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@Tag(name = "鉴权接口")
@Slf4j
public class AuthController {



    @PostMapping("/login")
    public R<LoginVo> login(@Validated  @RequestBody LoginDto loginDto) {
        log.info("login");
        if (!loginDto.getUserName().equals("FlashSloth")) {
            R.fail("用户名错误");
        }
        LoginVo vo = new LoginVo();
        vo.setToken("abc");
        vo.setRefreshToken("abcdef");
        return R.success(vo);
    }

    @GetMapping("/getUserInfo")
    public R<UserInfoVo> getUserInfo() {
        UserInfoVo vo = new UserInfoVo();
        vo.setUserId("1");
        vo.setUserName("FlashSloth");
        vo.setRoles(new ArrayList<>());
        vo.setButtons(new ArrayList<>());
        return R.success(vo);
    }

}
