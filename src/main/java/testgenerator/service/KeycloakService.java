package testgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.constants.AppConstants;
import testgenerator.model.params.SignUpParam;

import javax.ws.rs.core.Response;
import java.util.Collections;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    public Response addUser(SignUpParam param, CharSequence password) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(param.getEmail());

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        keycloakUser.setFirstName(param.getName());
        keycloakUser.setLastName(param.getSurname());
        keycloakUser.setEmail(param.getEmail());
        keycloakUser.setEnabled(true);
        keycloakUser.setEmailVerified(false);
        return usersResource.create(keycloakUser);
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
        keycloak.realm(AppConstants.REALM).deleteSession(sessionId);
    }

    private static CredentialRepresentation createPasswordCredentials(CharSequence password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();

        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password.toString());

        return passwordCredentials;
    }
}
