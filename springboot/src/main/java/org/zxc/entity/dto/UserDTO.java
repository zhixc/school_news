package org.zxc.entity.dto;

import lombok.Data;

/**
 * 由于我注册时，需要用到确认密码参数，
 * 所以这里写一个新的类
 * 来接收前端传过来的参数
 * @author 知行成
 */
@Data
public class UserDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 确认密码
     */
    private String password2;

    /**
     * 角色 id
     */
    private Integer roleId;
}
