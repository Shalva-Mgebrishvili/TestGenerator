package testgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.config.KeycloakConfig;
import testgenerator.model.domain.UserEntity;
import testgenerator.utils.KeycloakConfigContainer;

import java.util.Collections;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakConfigContainer keycloakConfigContainer;

    public void addUser(UserEntity user, CharSequence password) {
        UsersResource usersResource = KeycloakConfig.getInstance(keycloakConfigContainer).realm(keycloakConfigContainer.getRealm()).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(user.getEmail());

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        keycloakUser.setFirstName(user.getName());
        keycloakUser.setLastName(user.getSurname());
        keycloakUser.setEmail(user.getEmail());
        keycloakUser.setEnabled(true);
        keycloakUser.setEmailVerified(false);
        usersResource.create(keycloakUser);
    }

    public String getSessionId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        String sessionId = (String) jwt.getTokenAttributes().getOrDefault("session_state", "NOT");

        if (sessionId.equals("NOT")) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't find sessionId in token.");
        }

        return sessionId;
    }

    public void logout(String sessionId) {
        KeycloakConfig.getInstance(keycloakConfigContainer).realm(keycloakConfigContainer.getRealm()).deleteSession(sessionId);
    }

    private static CredentialRepresentation createPasswordCredentials(CharSequence password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();

        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password.toString());

        return passwordCredentials;
    }
}
