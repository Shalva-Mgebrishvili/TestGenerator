package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "topic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopicEntity extends  SuperEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private StackEntity stack;

}
