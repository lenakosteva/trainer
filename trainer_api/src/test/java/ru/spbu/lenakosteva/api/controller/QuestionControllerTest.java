package ru.spbu.lenakosteva.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.spbu.lenakosteva.api.dto.OpenQuestionCardDto;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class QuestionControllerTest {

    private static final Long ID = 1L;
    private static final String QUESTION = "Что такое Spring?";
    private static final String EXPECTED_ANSWER = "Весна";

    @MockBean
    private QuestionRepository repository;
    @Autowired
    private QuestionController controller;

    @Test
    @DisplayName("Получение вопроса по корректному ID возвразает ожидаемый результат")
    public void test_get_by_id_returns_expected_dto() {
        OpenQuestionCard questionCard = new OpenQuestionCard(ID, QUESTION, EXPECTED_ANSWER);
        OpenQuestionCardDto expectedDto = new OpenQuestionCardDto(ID, QUESTION, EXPECTED_ANSWER);
    
        when(repository.getById(ID)).thenReturn(Optional.of(questionCard));

        Optional<OpenQuestionCardDto> result = controller.getById(ID);

        assertTrue(result.isPresent());
        assertEquals(expectedDto, result.get());
    }

    @Test
    @DisplayName("Получение вопроса по не существующему ID возвращает пустой Optional")
    public void test_get_by_nonexistent_id_returns_empty_optional() {
        Long nonExistentId = 999L;
        when(repository.getById(nonExistentId)).thenReturn(Optional.empty());

        Optional<OpenQuestionCardDto> result = controller.getById(nonExistentId);

        assertFalse(result.isPresent());
    }

    @Test
    @DisplayName("Получение всех вопросов возвращает список dto")
    public void test_get_all_returns_list_of_dtos() {
        List<OpenQuestionCard> questionCards = List.of(
            new OpenQuestionCard(ID, QUESTION, EXPECTED_ANSWER),
            new OpenQuestionCard(2L, "Question 2?", "Answer 2")
        );
        List<OpenQuestionCardDto> expectedDtos = List.of(
            new OpenQuestionCardDto(ID, QUESTION, EXPECTED_ANSWER),
            new OpenQuestionCardDto(2L, "Question 2?", "Answer 2")
        );
        when(repository.getAll()).thenReturn(questionCards);

        List<OpenQuestionCardDto> result = controller.getAll();

        assertEquals(expectedDtos, result);
    }

    @Test
    @DisplayName("Сохранение нового вопроса вызывает метод add в репозитории")
    public void test_save_calls_question_service_with_mapped_model() {
        OpenQuestionCardDto dto = new OpenQuestionCardDto(ID, QUESTION, EXPECTED_ANSWER);
        OpenQuestionCard questionCard = new OpenQuestionCard(ID, QUESTION, EXPECTED_ANSWER);
        when(repository.getById(ID)).thenReturn(Optional.empty());

        controller.save(dto);

        Mockito.verify(repository).add(questionCard);
    }

    @Test
    @DisplayName("Удаление вопроса вызывает метод remove в репозитории")
    public void test_delete_calls_service_delete_with_id() {
        controller.delete(ID);

        Mockito.verify(repository).remove(ID);
    }

    @Test
    @DisplayName("Получение вопроса, когда ID=null возвращает пустной Optional")
    public void test_get_by_null_id_returns_empty_optional() {
        Long id = null;
        when(repository.getById(id)).thenReturn(Optional.empty());

        Optional<OpenQuestionCardDto> result = controller.getById(id);

        assertFalse(result.isPresent());
    }
}