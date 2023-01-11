package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "seniority")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Seniority extends SuperEntity {
    @Column(name = "seniority_level", nullable = false)
    private String seniorityLevel;

}
