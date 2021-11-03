package org.zxc.config.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * SpringMVC 配置扩展
 *
 */
@Configuration // 交给 Spring 管理
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 跨域配置，解决跨域问题
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //设置允许跨域的路由
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                //设置允许的方法
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                //是否允许证书（cookies）
                .allowCredentials(true)
                //允许跨域的时间
                .maxAge(3600)
                .allowedHeaders("*");
    }

    /**
     * 我试了一下，UserService 或者UserMapper
     * 好像不能直接注入 AdminHandlerInterceptor 中，
     * 查询用户名时会报空指针异常，所以在这里注入试试
     * 作为参数传递过去
     *
     * 2021.07.29已经解决这个问题了
     */
    //@Autowired
    //UserService userService;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("index");
        //registry.addViewController("/index.html").setViewName("index");
        //registry.addViewController("/main.html").setViewName("dashboard");
    }

    /**
     * 配置拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 下面的拦截器拦截顺序：登录拦截器 => 新闻管理员拦截器 => 管理员拦截器

        /**
         * 登录拦截器
         */
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**") // 拦截的请求
                .excludePathPatterns("/login"
                        , "/news/list"  // 放行查询新闻列表信息
                        , "/news/{id}"  // 放行根据 id 查询新闻
                        , "/logout"  // 放行退出接口
                        , "/swagger-ui.html" // 放行swagger测试页面
                        , "/api/kaptcha/defaultKaptcha"); // 放行的请求
        /**
         * 新闻管理员拦截器
         */
        registry.addInterceptor(new NewsManagerHandlerInterceptor())
                .addPathPatterns("/news/save", "/news/update", "/delete/{id}") // 拦截 NewsController 中的一些请求
                .excludePathPatterns(); // 放行的请求

        /**
         * 管理员拦截器
         */
        registry.addInterceptor(new AdminHandlerInterceptor())
                .addPathPatterns("/user/**") // 拦截 UserController 中的所有请求
                .excludePathPatterns(); // 放行的请求
    }


}
