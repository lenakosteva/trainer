package ru.spbu.lenakosteva.spring.jdbc.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcTemplateDao implements QuestionRepository {

    private static final String DDL_QUERY = """
          CREATE TABLE questions (
                id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
                question            VARCHAR(100),
                expected_answer     VARCHAR(50))
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, question, expected_answer FROM questions
          """;
    private static final String FIND_BY_ID_QUERY = """
          SELECT * FROM questions WHERE id = ?
          """;
    private static final String INSERT_QUESTION_QUERY = """
          INSERT INTO questions(id, question, expected_answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_QUESTION_QUERY = """
          UPDATE questions SET question=?, expected_answer=? WHERE id=?
          """;
    private static final String DELETE_QUESTION_QUERY = """
          DELETE FROM questions WHERE id=?
          """;
    private final JdbcTemplate jdbcTemplate;

    public QuestionJdbcTemplateDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        initSchema();
    }

    @Override
    public List<OpenQuestionCard> getAll() {
        return jdbcTemplate.query(
                FIND_ALL_QUERY,
                (ResultSet rs, int rowNum) -> constructQuestion(rs)
        );
    }

    @Override
    public Optional<OpenQuestionCard> getById(Long id) {
        List<OpenQuestionCard> questions = jdbcTemplate.query(
                FIND_BY_ID_QUERY,
                (ResultSet rs, int rowNum) -> constructQuestion(rs),
                id
        );

        return questions.isEmpty()
                ? Optional.empty()
                : Optional.of(questions.get(0));
    }

    @Override
    public void add(OpenQuestionCard openQuestionCard) {
        jdbcTemplate.update(
                INSERT_QUESTION_QUERY,
                openQuestionCard.getId(), openQuestionCard.getQuestion(), openQuestionCard.getExpectedAnswer()
        );
    }

    @Override
    public void update(OpenQuestionCard openQuestionCard) {
        jdbcTemplate.update(
                UPDATE_QUESTION_QUERY,
                openQuestionCard.getQuestion(), openQuestionCard.getExpectedAnswer(), openQuestionCard.getId()
        );
    }

    @Override
    public void remove(Long id) {
        jdbcTemplate.update(DELETE_QUESTION_QUERY, id);
    }

    private OpenQuestionCard constructQuestion(ResultSet resultSet) throws SQLException {
        return new OpenQuestionCard(
                resultSet.getLong("id"),
                resultSet.getString("question"),
                resultSet.getString("expected_answer")
        );
    }

    private void initSchema() {
        jdbcTemplate.update(DDL_QUERY);
    }
}
