package ru.spbu.lenakosteva.domain.service;

import org.springframework.stereotype.Service;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.repo.QuestionRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository repository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public List<OpenQuestionCard> getAll() {
        return repository.getAll();
    }

    public Optional<OpenQuestionCard> getById(Long id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        } else {
            return repository.getById(id);
        }
    }

    public boolean contains(OpenQuestionCard openQuestionCard) {
        if (Objects.isNull(openQuestionCard)) {
            return false;
        }

        return repository.getById(openQuestionCard.getId()).isPresent();
    }

    public void save(OpenQuestionCard openQuestionCard) {
        if (Objects.isNull(openQuestionCard)) {
            return;
        }

        if (contains(openQuestionCard)) {
            repository.update(openQuestionCard);
        } else {
            repository.add(openQuestionCard);
        }
    }

    public void delete(Long id) {
        if (Objects.isNull(id)) {
            return;
        }
        repository.remove(id);
    }
}
