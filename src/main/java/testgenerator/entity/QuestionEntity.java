package testgenerator.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "question")
@Data
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String stack;

    @Column
    private String level;

    @Column
    private String subject;

    @Column
    private String questionText;

}
