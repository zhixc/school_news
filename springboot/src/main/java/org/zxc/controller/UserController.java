package org.zxc.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.zxc.common.lang.Result;
import org.zxc.entity.User;
import org.zxc.service.UserService;

import java.util.Date;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author 知行成
 * @since 2021-07-24
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 分页查询用户信息
     * @param currentPage 当前页
     * @param pageSize 每页显示多少条数据
     * @return
     */
    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage,
                       @RequestParam(defaultValue = "3") Integer pageSize) {
        /**
         * 调用page()方法，将currentPage, pageSize传入
         */
        IPage<User> userIPage = userService.page(new Page<User>(currentPage, pageSize));
        /**
         * 返回查询数据
         */
        return Result.success(userIPage);
    }

    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        User user1 = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if (user1 != null) {
            return Result.fail(403,"错误，用户名已经存在！请重新输入", null);
        }
        user.setCreateTime(new Date());
        user.setRoleId(3);
        user.setStatus(1);
        userService.save(user);
        return Result.success("添加成功！");
    }

    /**
     * 修改用户
     * @param user 新的用户对象，即修改后的用户对象信息
     * @return
     */
    @PutMapping("/update")
    public Result update(@RequestBody User user) {
        // 根据 id 查询数据库中原本的用户信息
        User byId = userService.getById(user.getUserId());
        // 下面 if 判断 用户是否改变了用户名
        if (!byId.getUsername().equals(user.getUsername())) {
            // 改变了用户名
            // 根据 用户名查询数据库中用户名是否已存在
            User username = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
            if (username != null) { // 说明修改后的用户名在数据库中已经存在
                return Result.fail("用户名已经存在，请重新输入！");
            }
        }
        user.setUpdateTime(new Date());
        userService.updateById(user);
        return Result.success("修改成功!");
    }

    /**
     * 删除用户
     * @param id 要删除的用户 id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        if (id == null) {
            return Result.fail(403, "错误，没有选中要删除的行！", null);
        }else {
            userService.removeById(id);
            return Result.success("删除成功！");
        }
    }
}
