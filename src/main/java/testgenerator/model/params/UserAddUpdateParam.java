package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.model.enums.Role;

@Getter
@Setter
public class UserAddUpdateParam {

    private String name;

    private String surname;

    private String email;

    private Role role;
}
