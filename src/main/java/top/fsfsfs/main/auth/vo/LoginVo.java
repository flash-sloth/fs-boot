package top.fsfsfs.main.auth.vo;

import lombok.Data;

@Data
public class LoginVo {
    private String token;
    private String refreshToken;
}
