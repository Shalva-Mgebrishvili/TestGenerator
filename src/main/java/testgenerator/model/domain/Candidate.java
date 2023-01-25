package testgenerator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends SuperEntity {

//    @Column(name = "name")
//    private String name;
//
//    @Column(name = "surname")
//    private String surname;
//
//    @Column(name = "email")
//    private String email;

    @OneToMany(mappedBy = "candidate")
    private List<TestResult> testResults;

    @OneToOne(mappedBy = "candidate")
    private UserEntity user;
}
