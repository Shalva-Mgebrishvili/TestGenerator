package testgenerator.model.params;

import lombok.Getter;
import lombok.Setter;
import testgenerator.utils.annotation.Email;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateParam {

    @NotBlank(message = "Username is required.")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters long.")
    private String username;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "Surname is required.")
    private String surname;

    @Email
    private String email;

}
