package top.fsfsfs.boot.modules.auth.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVo {
    private String userId ;
    private String userName;
    private List<String> roles;
    private List<String> buttons;
}
