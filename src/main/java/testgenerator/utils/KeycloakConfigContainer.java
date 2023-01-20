package testgenerator.utils;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class KeycloakConfigContainer {

    @Value("${  security:\n" +
            "    oauth2:\n" +
            "      client:\n" +
            "        registration:\n" +
            "          keycloak:\n" +
            "            client-id}")
    private String clientId;

    @Value("${  security:\n" +
            "    oauth2:\n" +
            "      client:\n" +
            "        registration:\n" +
            "          keycloak:\n" +
            "            client-secret}")
    private String clientSecret;

}
