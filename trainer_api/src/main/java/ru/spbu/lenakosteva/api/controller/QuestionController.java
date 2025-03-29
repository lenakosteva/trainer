package ru.spbu.lenakosteva.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.spbu.lenakosteva.api.dto.OpenQuestionCardDto;
import ru.spbu.lenakosteva.api.mapper.QuestionMapperDto;
import ru.spbu.lenakosteva.domain.service.QuestionService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/question")
@Tag(name = "Questions", description = "Endpoint'ы, связанные с вопросами")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapperDto mapper;

    public QuestionController(QuestionService questionService, QuestionMapperDto mapper) {
        this.questionService = questionService;
        this.mapper = mapper;
    }

    @Operation(summary = "Получение одного вопроса",
            description = "Получен вопрос по его ID"
    )
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Optional<OpenQuestionCardDto> getById(@Parameter(description = "ID искомого вопроса") @PathVariable Long id) {
        return questionService.getById(id).map(mapper::toDto);
    }

    @Operation(summary = "Получение всех вопросов",
            description = "Получены все вопросы без ограничений"
    )
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OpenQuestionCardDto> getAll() {
        return mapper.toDto(questionService.getAll());
    }

    @Operation(summary = "Добавление нового вопроса",
            description = "Создает новый вопрос"
    )
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public void save(@Parameter(description = "Новый вопрос") @RequestBody OpenQuestionCardDto dto) {
        questionService.save(mapper.toModel(dto));
    }

    @Operation(summary = "Удаление вопроса",
            description = "Находит вопрос по ID и удаляет, если он найден"
    )
    @DeleteMapping("/{id}")
    public void delete(@Parameter(description = "ID вопроса для удаления") @PathVariable Long id) {
        questionService.delete(id);
    }
}
