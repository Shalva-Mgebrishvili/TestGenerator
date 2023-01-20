package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.utils.annotation.Email;
import testgenerator.utils.annotation.Password;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SignUpParam {

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Surname is required.")
    private String surname;

    @Email
    private String email;

    @Password
    private CharSequence password;
}
