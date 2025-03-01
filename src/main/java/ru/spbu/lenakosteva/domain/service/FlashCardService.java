package ru.spbu.lenakosteva.domain.service;

import org.springframework.stereotype.Service;
import ru.spbu.lenakosteva.domain.model.FlashCard;
import ru.spbu.lenakosteva.domain.repo.FlashCardRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FlashCardService {
    private final FlashCardRepository repository;

    public FlashCardService(FlashCardRepository repository) {
        this.repository = repository;
    }

    public List<FlashCard> getAll() {
        return repository.getAll();
    }

    public Optional<FlashCard> getById(Long id) {
        if (Objects.isNull(id)) {
            return Optional.empty();
        } else {
            return repository.getById(id);
        }
    }

    public boolean contains(FlashCard flashCard) {
        if (Objects.isNull(flashCard)) {
            return false;
        }

        return repository.getById(flashCard.getId()).isPresent();
    }

    public void save(FlashCard flashCard) {
        if (Objects.isNull(flashCard)) {
            return;
        }

        if (contains(flashCard)) {
            repository.update(flashCard);
        } else {
            repository.add(flashCard);
        }
    }

    public void delete(Long id) {
        if (Objects.isNull(id)) {
            return;
        }
        repository.remove(id);
    }
}
