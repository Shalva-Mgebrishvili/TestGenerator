package testgenerator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer oAuth Token"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer oAuth Token",
                                        new SecurityScheme()
                                                .name("auth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(new Info()
                        .title("test_generator")
                        .description("Test Generator App")
                        .version("0.0.1"));
    }

}