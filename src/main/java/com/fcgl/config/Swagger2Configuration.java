package com.fcgl.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author furg@senthink.com
 * @date 2018/11/29
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {

    private static final Set<String> DEFAULT_PROTOCOLS = Sets.newHashSet("http","https");

    private static final Set<String> DEAULT_PRODUCES_AND_CONSUMES = Sets.newHashSet("application/json");


    /**
     * 管理平台接口文档
     */

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .produces(DEAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEAULT_PRODUCES_AND_CONSUMES)
                .host("10.200.207:9000")
                .protocols(DEFAULT_PROTOCOLS)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fcgl.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("系统平台接口文档")
                .description("更多文档请参考: https://aistexg.coding.me")
                .version("1.0-sys")
                .termsOfServiceUrl("https://aistexg.coding.me")
                .contact(new Contact("付荣刚", "https://www.senthink.com", "furg@senthink.com"))
                .license("Apache-2.0")
                .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }


    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.any())
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}