package ru.spbu.lenakosteva.storage;

import org.springframework.stereotype.Repository;
import ru.spbu.lenakosteva.domain.model.FlashCard;
import ru.spbu.lenakosteva.domain.repo.FlashCardRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class InMemoryFlashCardRepository implements FlashCardRepository {
    private final Map<Long, FlashCard> storage;

    public InMemoryFlashCardRepository() {
        storage = new HashMap<>();
    }

    @Override
    public List<FlashCard> getAll() {
        return storage.values().stream().toList();
    }

    @Override
    public Optional<FlashCard> getById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public void add(FlashCard flashCard) {
        storage.put(flashCard.getId(), flashCard);
    }

    @Override
    public void update(FlashCard flashCard) {
        storage.put(flashCard.getId(), flashCard);
    }

    @Override
    public void remove(Long id) {
        storage.remove(id);
    }
}
