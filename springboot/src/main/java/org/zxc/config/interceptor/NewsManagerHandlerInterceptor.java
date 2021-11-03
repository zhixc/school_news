package org.zxc.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.zxc.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新闻管理员拦截器
 */
public class NewsManagerHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 从 session 中取出已经存起来的用户信息
         */
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (loginUser == null){
            // 说明用户没有登录，不放行
            System.out.println("新闻管理员拦截器已经成功拦截！");
            return false;
        }

        if (loginUser.getRoleId() == 1 || loginUser.getRoleId() == 2) {
            // 进入这里，说明用户是 管理员 或 新闻管理员
            // 放行
            System.out.println("当前权限=" + loginUser.getRoleId());
            return true;
        }
        System.out.println("新闻管理员拦截器已经成功拦截！");

        return false;
    }
}
