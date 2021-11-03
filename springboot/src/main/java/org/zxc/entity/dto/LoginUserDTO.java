package org.zxc.entity.dto;

import lombok.Data;

/**
 * 用户登录类
 * @author 知行成
 */
@Data
public class LoginUserDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String newVerificationCode;
}
