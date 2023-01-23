package testgenerator.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import testgenerator.constants.AppConstants;

@Configuration
public class KeycloakConfig  {

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @Bean
    public Keycloak getInstance(){
        return KeycloakBuilder.builder()
                .serverUrl(AppConstants.AUTH_SERVER_URL)
                .realm(AppConstants.REALM)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
        }
}
