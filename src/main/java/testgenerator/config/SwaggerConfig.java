package testgenerator.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {




    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("Bearer oAuth Token").addList("kkk"))
                .components(
                        new Components()
                                .addSecuritySchemes("Bearer oAuth Token",
                                        new SecurityScheme()
                                                .name("auth")
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                                .addSecuritySchemes( "kkk",
                                        new SecurityScheme()
                                                .name("oauth")
                                                .type(SecurityScheme.Type.OAUTH2)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                                .flows(
                                                   new OAuthFlows()
                                                           .authorizationCode(
                                                                   authorizationCodeFlow()
                                                           )
                                                )

                                )
                )
                .info(new Info()
                        .title("test_generator")
                        .description("Test Generator App")
                        .version("0.0.1"));
    }

    private Scopes scopes() {
        return new Scopes()
                .addString("openid", "openid")
                .addString("profile", "profile")
                .addString("email", "email")
                .addString("offline_access", "offline_access")
                .addString("user_info", "user_info");
    }

    private OAuthFlow authorizationCodeFlow() {
        return new OAuthFlow()
                .authorizationUrl("http://localhost:8080/realms/testgeneratorapp/protocol/openid-connect/auth")
                .tokenUrl("http://localhost:8080/realms/testgeneratorapp/protocol/openid-connect/token")
                .refreshUrl("http://localhost:8080/realms/testgeneratorapp/protocol/openid-connect/token")
                .scopes(scopes());
    }

}