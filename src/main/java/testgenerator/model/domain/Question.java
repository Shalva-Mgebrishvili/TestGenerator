package testgenerator.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import testgenerator.model.enums.QuestionType;

import javax.persistence.*;


@Entity
@Table(name = "question")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends SuperEntity {

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "point", nullable = false)
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type", nullable = false)
    private QuestionType questionType;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "seniority_id", nullable = false)
    private Seniority seniority;

}
