package ru.spbu.lenakosteva.domain.model;

import java.util.Objects;

public class FlashCard {
    private final String question;
    private final String expectedAnswer;

    public FlashCard(String question, String expectedAnswer) {
        if (Objects.isNull(question) || question.isEmpty()) {
            throw new IllegalArgumentException("Question is null or empty");
        }

        if (Objects.isNull(expectedAnswer) || expectedAnswer.isEmpty()) {
            throw new IllegalArgumentException("Expected answer is null or empty");
        }

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
}
