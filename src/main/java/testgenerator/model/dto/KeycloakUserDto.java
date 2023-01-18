package testgenerator.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KeycloakUserDto {
    private String name;
    private String surname;
    private String password;
    private String email;
}
