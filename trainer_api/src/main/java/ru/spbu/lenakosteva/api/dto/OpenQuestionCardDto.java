package ru.spbu.lenakosteva.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Карточка с вопросом")
public class OpenQuestionCardDto {
    @Schema(description = "ID вопроса", example = "1")
    private Long id;

    @Schema(description = "Вопрос, на который нужно ответить", example = "Сколько будет 2 + 2?")
    private String question;

    @Schema(description = "Ожидаемый ответ на вопрос", example = "4")
    private String expectedAnswer;

    public OpenQuestionCardDto(Long id, String question, String expectedAnswer) {
        this.id = id;
        this.question = question;
        this.expectedAnswer = expectedAnswer;
    }

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

    public String displayedName() {
        return String.format("%s. %s", id, question);
    }
}
