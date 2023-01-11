package testgenerator.model.domain;

import lombok.*;
import testgenerator.model.enums.Role;

import javax.persistence.*;

@Entity
@Table(name = "my_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends  SuperEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

}
