package testgenerator.model.domain;

import lombok.*;
import testgenerator.model.enums.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "my_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends  SuperEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<TestResult> testResults;

    @ManyToMany
    @JoinTable(name = "user_stack",
            joinColumns = @JoinColumn(name = "stack_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<Stack> stacks;

}
