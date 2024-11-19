package com.ssawallafy.workalone_backend.global.config.swagger;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Profile;

@Configuration
public class SwaggerConfig {
    //	@Bean
//	public OpenAPI openAPI() {
//		// String jwt = "JWT";
//		// SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);
//		// Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
//		// 	.name(jwt)
//		// 	.type(SecurityScheme.Type.HTTP)
//		// 	.scheme("bearer")
//		// 	.bearerFormat("JWT")
//		// );
//		return new OpenAPI()
//			.components(new Components())
//			.info(apiInfo());
//			// .addSecurityItem(securityRequirement)
//			// .components(components);
//	}
    @Bean
    @Profile("local")  // 로컬 환경에서는 HTTP로 설정
    public OpenAPI localOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("http://localhost:8080"))
                .components(new Components())
                .info(apiInfo());
    }

    @Bean
    @Profile("prod")  // 운영 환경에서는 HTTPS로 설정
    public OpenAPI prodOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url("https://k11s201.p.ssafy.io"))
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("WorkAlone API") // API의 제목
                .description("WorkAlone의 Swagger") // API에 대한 설명
                .version("1.0.0"); // API의 버전
    }


}
