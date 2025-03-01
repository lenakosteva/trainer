package ru.spbu.lenakosteva.dao;

import org.springframework.stereotype.Repository;
import ru.spbu.lenakosteva.domain.model.FlashCard;
import ru.spbu.lenakosteva.domain.repo.FlashCardRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcFlashCardDao implements FlashCardRepository {

    private static final String DDL_QUERY = """
          CREATE TABLE cards (
                id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
                question            VARCHAR(100),
                expected_answer     VARCHAR(50))
          """;
    private static final String FIND_ALL_QUERY = """
          SELECT id, question, expected_answer FROM cards
          """;
    private static final String FIND_BY_ID_QUERY = """
          SELECT * FROM cards WHERE id = ?
          """;
    private static final String INSERT_CARD_QUERY = """
          INSERT INTO cards(id, question, expected_answer) VALUES (?, ?, ?)
          """;
    private static final String UPDATE_CARD_QUERY = """
          UPDATE cards SET question=?, expected_answer=? WHERE id=?
          """;
    private static final String DELETE_CARD_QUERY = """
          DELETE FROM cards WHERE id=?
          """;

    private final DataSource dataSource;

    public JdbcFlashCardDao(DataSource dataSource) {
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
    public List<FlashCard> getAll() {
        List<FlashCard> cards = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                FlashCard card = constructFlashCard(resultSet);
                cards.add(card);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    @Override
    public Optional<FlashCard> getById(Long id) {
        List<FlashCard> cards = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                FlashCard card = constructFlashCard(resultSet);
                cards.add(card);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards.isEmpty() ? Optional.empty() : Optional.of(cards.get(0));
    }

    @Override
    public void add(FlashCard flashCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(INSERT_CARD_QUERY);){
            statement.setLong(1, flashCard.getId());
            statement.setString(2, flashCard.getQuestion());
            statement.setString(3, flashCard.getExpectedAnswer());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(FlashCard flashCard) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARD_QUERY);){
            statement.setString(1, flashCard.getQuestion());
            statement.setString(2, flashCard.getExpectedAnswer());
            statement.setLong(3, flashCard.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CARD_QUERY);){
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private FlashCard constructFlashCard(ResultSet resultSet) throws SQLException {
        return new FlashCard(
                resultSet.getLong("id"),
                resultSet.getString("question"),
                resultSet.getString("expected_answer")
        );
    }
}
