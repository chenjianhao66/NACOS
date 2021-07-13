package com.jianhao.config;


//import io.swagger.annotations.Contact;
//import io.swagger.models.Contact;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//import springfox.dumentation.swagger2.annotations.EnableSwagger2;
//import sp

@Configuration
@EnableSwagger2
public class swaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jianhao.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                        .title("SpringCloud alibaba nacos test")
                        .description("SpringCloud alibaba nacos zhenghe")
                        .version("9.0")
                        .contact(new Contact("jianhao","www.baidu.com","jianhao.c@qq.com"))
                        .license("The jianhao License")
                        .licenseUrl("http://www.baidu.com")
                        .build());
    }

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
