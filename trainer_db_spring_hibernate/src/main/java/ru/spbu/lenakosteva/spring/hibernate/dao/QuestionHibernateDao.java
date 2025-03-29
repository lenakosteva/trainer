package ru.spbu.lenakosteva.spring.hibernate.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.repo.QuestionRepository;
import ru.spbu.lenakosteva.spring.hibernate.entity.OpenQuestionCardEntity;
import ru.spbu.lenakosteva.spring.hibernate.mapper.QuestionMapper;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class QuestionHibernateDao implements QuestionRepository {

    private static final Logger logger = LogManager.getLogger(QuestionHibernateDao.class);
    private final QuestionMapper mapper;

    @PersistenceContext
    private EntityManager entityManager;

    public QuestionHibernateDao(QuestionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<OpenQuestionCard> getAll() {
        logger.debug("Выбираем всех");
        List<OpenQuestionCardEntity> entities = entityManager
                .createQuery("SELECT question FROM OpenQuestionCardEntity question", OpenQuestionCardEntity.class)
                .getResultList();
        return mapper.mapToModel(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OpenQuestionCard> getById(Long id) {
        List<OpenQuestionCardEntity> entity = entityManager
                .createQuery("SELECT question FROM OpenQuestionCardEntity question WHERE question.id = ?1", OpenQuestionCardEntity.class)
                .setParameter(1, id)
                .getResultList();
        if (!entity.isEmpty()) {
            OpenQuestionCard model = mapper.mapToModel(entity.get(0));
            return Optional.of(model);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void add(OpenQuestionCard openQuestionCard) {
        OpenQuestionCardEntity entity = mapper.mapToEntity(openQuestionCard);
        entityManager.persist(entity);
    }

    @Override
    public void update(OpenQuestionCard openQuestionCard) {
        OpenQuestionCardEntity entity = mapper.mapToEntity(openQuestionCard);
        entityManager.persist(entity);
    }

    @Override
    public void remove(Long id) {
        OpenQuestionCardEntity entity = entityManager
                .createQuery("SELECT question FROM OpenQuestionCard question WHERE question.id = ?1", OpenQuestionCardEntity.class)
                .setParameter(1, id)
                .getSingleResult();
        entityManager.remove(entity);
    }
}
