package testgenerator.model.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "test_stack")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestStackEntity extends SuperEntity {

    @ManyToOne
    @JoinColumn(name = "stack_id")
    private StackEntity stack;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private TestEntity test;
}
