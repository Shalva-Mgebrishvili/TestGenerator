package testgenerator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import testgenerator.config.KeycloakConfig;
import testgenerator.model.dto.KeycloakUserDto;
import testgenerator.utils.KeycloakConfigContainer;

import java.util.Collections;


@Service
@Slf4j
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakConfigContainer keycloakConfigContainer;
//    private final AuthzClient authzClient;
    private final Keycloak keycloakClient;

    public void addUser(KeycloakUserDto user) {
        UsersResource usersResource = KeycloakConfig.getInstance(keycloakConfigContainer).realm(keycloakConfigContainer.getRealm()).users();

        CredentialRepresentation credentialRepresentation = createPasswordCredentials(user.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(user.getEmail());

        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(user.getName());
        kcUser.setLastName(user.getSurname());
        kcUser.setEmail(user.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);
        usersResource.create(kcUser);
    }

    private static CredentialRepresentation createPasswordCredentials(String password) {
        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);
        passwordCredentials.setValue(password);
        return passwordCredentials;
    }

//    public AccessTokenResponse login(LoginParam loginParam) {
//
//        String scope = loginParam.isRememberMe() ? "offline_access" : null;
//
//        AccessTokenResponse response;
//
//        try {
//            response = authzClient.authorization(loginParam.getEmail(), loginParam.getPassword().toString(), scope).authorize();
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user credentials");
//        }
//
//        return response;
//    }

//    public void logout(String sessionId) {
//        try {
//            keycloakClient.realm()
//        }
//    }
}
