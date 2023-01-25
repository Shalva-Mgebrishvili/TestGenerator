package testgenerator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testgenerator.model.domain.Candidate;
import testgenerator.model.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private Role role;

    private CandidateDto candidate;

}
