package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.utils.annotation.Email;

@Getter
@Setter
public class UserAddUpdateParam {

    private String name;

    private String surname;

    @Email
    private String email;

}
