package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "test_stack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStack extends SuperEntity {

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;
}
