package testgenerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import testgenerator.utils.KeycloakRoleConvertor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurity {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter((new KeycloakRoleConvertor()));

        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers(
                        "/auth/signup",
                        "/swagger-ui/**",
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/actuator/**",
                        "/swagger-ui.html/**"

                ).permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2ResourceServer()
                .jwt().jwkSetUri("http://localhost:8080/realms/testgeneratorapp/protocol/openid-connect/certs")
                .jwtAuthenticationConverter(jwtAuthenticationConverter)
                .and()
                .and().build();
    }
}
