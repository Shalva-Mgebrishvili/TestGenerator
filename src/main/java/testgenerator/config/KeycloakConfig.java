package testgenerator.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import testgenerator.constants.AppConstants;
import testgenerator.utils.KeycloakConfigContainer;

public class KeycloakConfig  {

    @Bean
    public static Keycloak getInstance(KeycloakConfigContainer keycloakConfigContainer){
        return KeycloakBuilder.builder()
                .serverUrl(AppConstants.AUTH_SERVER_URL)
                .realm(AppConstants.REALM)
                .grantType(OAuth2Constants.AUTHORIZATION_CODE)
                .clientId(keycloakConfigContainer.getClientId())
                .clientSecret(keycloakConfigContainer.getClientSecret())
                .build();
        }
}
