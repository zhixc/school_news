package org.zxc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import java.util.ArrayList;

/**
 * Swagger配置类，交给 Spring 管理
 * 默认访问：http://localhost:8080/swagger-ui.html
 * 如果springboot项目配置文件中修改了端口号，那么应该要修改8080为新的端口号
 */
@Configuration // 交给 Spring 管理
@EnableSwagger2 // 开启 Swagger2
public class SwaggerConfig {

    /**
     * 配置 Swagger2 的 Docket 的 bean 实例
     * @return
     */
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // .enable(false) 可以关闭Swagger，浏览器就访问不了了
                .select()
                // basePackage():指定扫描的接口所在包
                // any(): 扫描所有
                // none(): 不扫描
                // withClassAnnotation: 扫描类上的注解
                .apis(RequestHandlerSelectors.basePackage("org.zxc.controller"))
                // .path(): 过滤路径，这里过滤扫描zxc路径下的
                // .paths(PathSelectors.ant("/zxc/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("知行成", "", "");
        return new ApiInfo(
                "校园新闻管理系统",
                "API接口文档",
                "0.0.1",
                "urn:tos",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
