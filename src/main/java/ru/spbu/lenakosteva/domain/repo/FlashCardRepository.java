package ru.spbu.lenakosteva.domain.repo;

import ru.spbu.lenakosteva.domain.model.FlashCard;

import java.util.List;
import java.util.Optional;

public interface FlashCardRepository {
    List<FlashCard> getAll();
    Optional<FlashCard> getById(Long id);
    void add(FlashCard flashCard);
    void update(FlashCard flashCard);
    void remove(Long id);
}
