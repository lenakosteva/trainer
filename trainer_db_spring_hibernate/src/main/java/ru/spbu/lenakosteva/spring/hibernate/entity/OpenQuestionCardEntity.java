package ru.spbu.lenakosteva.spring.hibernate.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question")
public class OpenQuestionCardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String question;

    @Column(name = "answer")
    private String expectedAnswer;

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}
