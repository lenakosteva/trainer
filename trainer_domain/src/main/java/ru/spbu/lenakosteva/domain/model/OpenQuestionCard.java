package ru.spbu.lenakosteva.domain.model;

import java.util.Objects;

public class OpenQuestionCard {

    private final Long id;
    private final String question;
    private final String expectedAnswer;

    public OpenQuestionCard(Long id, String question, String expectedAnswer) {
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("Question is null or empty");
        }

        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("Expected answer is null or empty");
        }

        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("Id is null");
        }

        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean checkAnswer(String answer) {
        if (Objects.isNull(answer) || answer.isEmpty()) {
            throw new IllegalArgumentException("Answer is null or empty");
        }

        return answer.equals(expectedAnswer);
    }

    public Long getId() {
        return id;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + expectedAnswer + '\'' +
                '}';
    }
}
