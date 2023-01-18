package testgenerator.model.params;


import lombok.Getter;
import lombok.Setter;
import testgenerator.utils.annotation.Password;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class LoginParam {

    @NotBlank(message = "Email is required.")
    @Size(min = 4, max = 20, message = "Email must be between 4 and 20 characters long")
    private String email;

    @Password
    private CharSequence password;

    private boolean rememberMe = false;

}
