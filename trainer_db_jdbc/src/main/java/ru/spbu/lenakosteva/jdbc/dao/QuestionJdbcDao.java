package ru.spbu.lenakosteva.jdbc.dao;

import org.springframework.stereotype.Repository;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.repo.QuestionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestionJdbcDao implements QuestionRepository {

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

    private final DataSource dataSource;

    public QuestionJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
        initDataBase();
    }

    public void initDataBase() {
        try(Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(DDL_QUERY)) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public List<OpenQuestionCard> getAll() {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                OpenQuestionCard question = constructQuestion(resultSet);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    @Override
    public Optional<OpenQuestionCard> getById(Long id) {
        List<OpenQuestionCard> questions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                OpenQuestionCard question = constructQuestion(resultSet);
                questions.add(question);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void add(OpenQuestionCard openQuestionCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_QUESTION_QUERY);){
            statement.setLong(1, openQuestionCard.getId());
            statement.setString(2, openQuestionCard.getQuestion());
            statement.setString(3, openQuestionCard.getExpectedAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OpenQuestionCard openQuestionCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUESTION_QUERY);){
            statement.setString(1, openQuestionCard.getQuestion());
            statement.setString(2, openQuestionCard.getExpectedAnswer());
            statement.setLong(3, openQuestionCard.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUESTION_QUERY);){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private OpenQuestionCard constructQuestion(ResultSet resultSet) throws SQLException {
        return new OpenQuestionCard(
                resultSet.getLong("id"),
                resultSet.getString("question"),
                resultSet.getString("expected_answer")
        );
    }
}
