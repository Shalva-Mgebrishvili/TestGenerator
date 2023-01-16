package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "stack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stack extends SuperEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "stack")
    private List<Topic> topics;

}
