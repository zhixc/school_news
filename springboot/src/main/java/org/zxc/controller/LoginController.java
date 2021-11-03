package org.zxc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zxc.common.lang.Result;
import org.zxc.entity.User;
import org.zxc.entity.dto.LoginUserDTO;
import org.zxc.service.UserService;

import javax.servlet.http.HttpSession;

/**
 * 登录控制层
 * @author 知行成
 * @since 2021-07-24
 */
@Api(tags = "用户登录控制层")
@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @ApiOperation("用户登录接口") // Swagger文档接口注释
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDTO loginUserDTO, HttpSession session) {
        /**
         * @since 2021.07.29
         * 我已经找到跨域带来的 session 无效原因，
         * 并且通过后端添加一个MyMvcConfig相关设置，前端 axios 设置解决了这一问题，
         * Vue项目的main.js中配置
         * axios.defaults.withCredentials = true
         * 所以从现在开始可以将用户信息存储到 session 中了
         * 从 session 中取得 验证码，验证码一开始是在 KaptchaController 类中生成并且
         * 放到 session 中的，所以在 session 中可以取得
         */
        String  verificationCode = (String) session.getAttribute("verificationCode");

        System.out.println("session中的verificationCode = " + verificationCode);

        System.out.println("前端传过来的中的newVerificationCode = " + loginUserDTO.getNewVerificationCode());

        /**
         * 判断验证码是否一致，不一致的话返回消息：验证码错误
         */
        if (!verificationCode.equals(loginUserDTO.getNewVerificationCode())){
            return Result.fail("验证码错误！");
        }

        /**
         * 判断用户名是否为 null 或 ""
         * 如果是，那么说明用户没有输入用户名
         */
        if (loginUserDTO.getUsername() == null || loginUserDTO.getUsername().equals("")){
            return Result.fail(403, "错误，没有输入用户名!", null);
        }
        /**
         * 判断密码是否为 null 或 ""
         * 如果是，那么说明用户没有输入密码
         */
        if (loginUserDTO.getPassword() == null || loginUserDTO.getPassword().equals("")){
            return Result.fail(403, "错误，没有输入密码!", null);
        }
        /**
         * 根据用户名查询用户
         */
        User queryUser = userService.getOne(new QueryWrapper<User>().eq("username", loginUserDTO.getUsername()));
        if (queryUser == null) {// 判断用户是否在数据库中存在
            return Result.fail(401, "用户名不存在！", null);
        }else if (!queryUser.getPassword().equals(loginUserDTO.getPassword())){ // 判断密码
            return Result.fail(401, "密码错误！请重新输入密码！", null);
        }else {// 到这里说明用户名和密码与数据库中的匹配成功了
            /**
             * 2021.07.27
             * 我尝试过在这里将用户信息存到 session 中，但是不知道为什么
             * 在登录拦截器那里取出来的session中的用户信息为 null
             * 所以这里采用了另一种思路，将用户名存到了一个静态变量中
             * 这样登录拦截器就可以取到，而且管理员拦截器
             * AdminHandlerInterceptor也可以取到，这样就可以简单的实现
             * 权限管理，因为管理员拦截器只针对 UserController下面的进行拦截
             * 其余的都放行，当然我也知道这种方式存在许多严重的漏洞，如过有
             * 多个用户登录，那么这个方法就完蛋了，不过我暂时不想使用Spring Security
             * 来实现，所以出此下策，不使用框架的方式实现，有成就感一些，但是漏洞的存在
             * 又很难受 ><!
             */
            //Constant.loginUser.setUsername(queryUser.getUsername());

            /**
             * 2021.07.29
             * 今天已经解决因为跨域导致的 session 失效问题
             * 那么现在就可以将用户信息放到 session 中了
             */
            session.setAttribute("loginUser", queryUser);
            return Result.success(200, "登录成功!", queryUser);
        }
    }

    /**
     * 退出
     * @return
     */
    @GetMapping("/logout")
    public Result logout(HttpSession session) {
        // 清空用户名
        //Constant.loginUser.setUsername(null);
        User loginUser = (User) session.getAttribute("loginUser");
        System.out.println(loginUser);
        return Result.success("退出成功!");
    }

}
