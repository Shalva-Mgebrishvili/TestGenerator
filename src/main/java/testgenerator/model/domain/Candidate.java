package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends SuperEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email")
    private String email;

    @ManyToMany(mappedBy = "candidates")
    private List<Test> tests;

}
