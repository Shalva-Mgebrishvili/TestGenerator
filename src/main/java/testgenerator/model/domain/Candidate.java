package testgenerator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "candidate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate extends SuperEntity {

    @Column(name = "one_time_username")
    private String oneTimeUsername;

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
