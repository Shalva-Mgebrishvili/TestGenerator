package testgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import testgenerator.constants.AppConstants;
import testgenerator.model.domain.UserEntity;
import testgenerator.model.enums.Role;
import testgenerator.model.params.UserUpdateParam;

import javax.ws.rs.core.Response;
import java.util.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;
    private final String DATABASE_ID = "database_id";

    public Response addUserInKeycloak(UserEntity user, CharSequence password) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation keycloakUser = new UserRepresentation();

        if(user.getRole() != Role.CANDIDATE) {
            Map<String, List<String>> attributes = Map.of(
                    DATABASE_ID, List.of(user.getId().toString())
            );

            keycloakUser.setAttributes(attributes);
        }

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        keycloakUser.setUsername(user.getUsername());
        keycloakUser.setFirstName(user.getName());
        keycloakUser.setLastName(user.getSurname());
        keycloakUser.setEmail(user.getEmail());
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

    public Long getUserId() {
        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        Long userId = (Long)jwt.getToken().getClaims().get(DATABASE_ID);

        if (userId == null) {
            log.error("Unable to find database id in token for: {}", jwt.getName());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected situation, please contact support");
        }

        return userId;
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

    public void updateUserInKeycloak(UserEntity user, UserUpdateParam param) {
        UserResource userResource = keycloak.realm(AppConstants.REALM).users()
                .get(searchUserIdInKeycloakByUsername(user.getUsername()));

        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setUsername(param.getUsername());
        userRepresentation.setEmail(param.getEmail());
        userRepresentation.setFirstName(param.getName());
        userRepresentation.setLastName(param.getSurname());

        userResource.update(userRepresentation);
    }

    public Response deleteUserInKeycloak(String username) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        return usersResource.delete(searchUserIdInKeycloakByUsername(username));
    }

    public String searchUserIdInKeycloakByUsername(String username) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();
        List<UserRepresentation> search = usersResource.search(username, true);

        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keycloak user with this username doesn't exist");

        return usersResource.search(username, true).get(0).getId();
    }

    public void changeUserKeycloakRole (UserEntity user, String role) {
        UserResource userResource = keycloak.realm(AppConstants.REALM).users()
                .get(searchUserIdInKeycloakByUsername(user.getUsername()));

        removeKeycloakRoleFromUser(user.getRole().name(), userResource);

        List<RoleRepresentation> roleRepresentationList = userResource.roles().realmLevel().listAvailable();

        for (RoleRepresentation roleRepresentation : roleRepresentationList) {
            if (roleRepresentation.getName().equals(role.toUpperCase())) {
                userResource.roles().realmLevel().add(List.of(roleRepresentation));
            }
        }
    }

    private void removeKeycloakRoleFromUser(String role, UserResource userResource) {
        List<RoleRepresentation> roleToRemove = new LinkedList<>();
        roleToRemove.add(keycloak.realm(AppConstants.REALM).roles().get(role.toUpperCase()).toRepresentation());

        userResource.roles().realmLevel().remove(roleToRemove);
    }
}
