package com.oa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger2 Api配置
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 可以注入多个doket，也就是多个版本的api，可以在看到有三个版本groupName不能是重复的，v1和v2是ant风格匹配，配置文件
     * @return
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("v1").select()
                .apis(RequestHandlerSelectors.basePackage("com.oa.controller"))
                .paths(PathSelectors.ant("/**")).build().apiInfo(apiInfo());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("考勤打卡应用的Server端 APIs")
                .contact("oa")
                .version("v0.01")
                .build();
    }

}
