package top.fsfsfs.boot.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {
    @NotBlank(message = "用户姓名不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
}
