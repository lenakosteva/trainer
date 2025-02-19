package ru.spbu.lenakosteva.domain.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashCardTest {
    @Test
    @DisplayName("checkAnswer возвращает true при правильном ответе")
    void when_AnswerIsOk_then_checkAnswer_isTrue() {
        String question = "Вежливо попросить что то сделать на английском";
        String expectedAnswer = "Would You like to do something?";
        FlashCard card = new FlashCard(question, expectedAnswer);

        boolean result = card.checkAnswer(expectedAnswer);

        assertTrue(result);
    }

    @Test
    @DisplayName("checkAnswer возвращает false при неправильном ответе")
    void when_AnswerIsNotOk_then_checkAnswer_isFalse() {
        String question = "Вежливо попросить что то сделать на английском";
        String expectedAnswer = "Would You like to do something?";
        String actualAnswer = "Would you like do?";
        FlashCard card = new FlashCard(question, expectedAnswer);

        boolean result = card.checkAnswer(actualAnswer);

        assertFalse(result);
    }

    @Test
    @DisplayName("в checkAnswer передан null")
    void when_AnswerIsNull_then_checkAnswer_isFalse() {
        String question = "Вежливо попросить что то сделать на английском";
        String expectedAnswer = "Would You like to do something?";
        String actualAnswer = null;
        FlashCard card = new FlashCard(question, expectedAnswer);

        assertThrows(NullPointerException.class, () -> card.checkAnswer(actualAnswer));
    }

    @Test
    @DisplayName("в конструктор FlashCard переданы null")
    void when_QuestionAndExpectedAnswerAreNull_then_checkAnswer_() {
        String question = null;
        String expectedAnswer = null;
        String actualAnswer = "I know Java very well";
        FlashCard card = new FlashCard(question, expectedAnswer);

        boolean result = card.checkAnswer(actualAnswer);

        assertFalse(result);
    }
}