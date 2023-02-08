package testgenerator.model.domain;

import lombok.*;
import testgenerator.model.enums.QuestionStatus;
import testgenerator.model.enums.QuestionType;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Question extends SuperEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "point", nullable = false)
    private Double point;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_status", nullable = false)
    private QuestionStatus questionStatus;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "seniority_id", nullable = false)
    private Seniority seniority;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Answer> answers;

}
