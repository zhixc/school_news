package org.zxc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.zxc.common.lang.Result;
import org.zxc.entity.User;
import org.zxc.entity.dto.UserDTO;
import org.zxc.service.UserService;

import java.util.Date;

/**
 * 注册控制层
 * @author 知行成
 * @since 2021-07-24
 */
@RestController
public class RegisterController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody UserDTO userDTO) {
        /**
         * 判断用户名是否为 null 或 ""
         * 如果是，那么说明用户没有输入用户名
         */
        if (userDTO.getUsername() == null || userDTO.getUsername().equals("")){
            return Result.fail(403, "错误，没有输入用户名！", null);
        }
        /**
         * 判断密码是否为 null 或 ""
         * 如果是，那么说明用户没有输入密码
         */
        if (userDTO.getPassword() == null || userDTO.getPassword().equals("")){
            return Result.fail(403, "错误，没有输入密码！", null);
        }
        /**
         * 判断确认密码是否为 null 或 ""
         * 如果是，那么说明用户没有输入密码
         */
        if (userDTO.getPassword2() == null || userDTO.getPassword2().equals("")){
            return Result.fail(403, "错误，没有输入确认密码！", null);
        }
        /**
         * 判断密码和确认密码是否一致，若不一致，执行if里面的语句
         */
        if (!userDTO.getPassword().equals(userDTO.getPassword2())){
            return Result.fail(403, "错误，密码和确认密码不一致！", null);
        }
        /**
         * 查询数据库中用户名是否已经存在
         */
        User queryUser = userService.getOne(new QueryWrapper<User>().eq("username", userDTO.getUsername()));
        if (queryUser != null) {// 说明用户名已经在数据库中存在了
            return Result.fail(403, "错误，用户名已经被别人使用，请重新输入新的用户名", null);
        }else { // 这里说明用户对象为空
            User user = new User();
            user.setUsername(userDTO.getUsername());
            user.setPassword(userDTO.getPassword());
            user.setStatus(1);
            user.setRoleId(3); // 注册默认为普通用户
            user.setCreateTime(new Date());
            userService.save(user);// 存到数据库中
            return Result.success(200, "注册成功，请登录！", null);
        }
    }
}
