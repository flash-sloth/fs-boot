package top.fsfsfs.boot.modules.auth.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private String refreshToken;
}
