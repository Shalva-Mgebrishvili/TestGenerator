package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "stack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StackEntity extends SuperEntity {

    @Column(name = "name", nullable = false)
    private String name;

}
