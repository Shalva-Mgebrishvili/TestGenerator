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
import testgenerator.model.params.SignUpParam;
import testgenerator.model.params.UserAddUpdateParam;

import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final Keycloak keycloak;

    public Response addUserInKeycloak(SignUpParam param, CharSequence password) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(password);

        UserRepresentation keycloakUser = new UserRepresentation();

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));
        keycloakUser.setFirstName(param.getName());
        keycloakUser.setLastName(param.getSurname());
        keycloakUser.setEmail(param.getEmail());
        keycloakUser.setRealmRoles(Collections.singletonList("USER"));
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

    public void updateUserInKeycloak(UserEntity user, UserAddUpdateParam param) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        UserResource userResource = keycloak.realm(AppConstants.REALM).users()
                .get(searchUserIdInKeycloakByEmail(user.getEmail(), usersResource));

        UserRepresentation userRepresentation = userResource.toRepresentation();

        userRepresentation.setEmail(param.getEmail());
        userRepresentation.setFirstName(param.getName());
        userRepresentation.setLastName(param.getSurname());

        userResource.update(userRepresentation);
    }

    public Response deleteUserInKeycloak(String email) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        return usersResource.delete(searchUserIdInKeycloakByEmail(email, usersResource));
    }

    public String searchUserIdInKeycloakByEmail(String email, UsersResource usersResource) {
        List<UserRepresentation> search = usersResource.search(email, true);

        if (search.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keycloak user with this email doesn't exist");

        return usersResource.search(email, true).get(0).getId();
    }

    public void changeUserKeycloakRole (UserEntity user, String role) {
        UsersResource usersResource = keycloak.realm(AppConstants.REALM).users();

        UserResource userResource = keycloak.realm(AppConstants.REALM).users()
                .get(searchUserIdInKeycloakByEmail(user.getEmail(), usersResource));

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

//    public String getPrincipalKeycloakId() {
//        JwtAuthenticationToken jwt = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//
//        String keycloakId = jwt.getName();
//
//        if(keycloakId == null) throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authorization error");
//
//        return keycloakId;
//    }

//    public boolean checkOldPassword (PasswordChangeParam param, String userKeycloakId) {
//        boolean validPassword = false;
//
//        UserResource userResource = keycloak.realm(AppConstants.REALM).users().get(userKeycloakId);
//        userResource.
//        String email = userResource.toRepresentation().getEmail();
//
//        try {
//            AccessTokenResponse  response =
//        }
//    }
}
