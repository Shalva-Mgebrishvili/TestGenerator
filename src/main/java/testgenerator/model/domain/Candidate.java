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

    @OneToMany(mappedBy = "candidate")
    private List<TestResult> testResults;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
