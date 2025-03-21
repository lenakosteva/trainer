package ru.spbu.lenakosteva.gui.controller;

import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.service.QuestionService;
import ru.spbu.lenakosteva.gui.model.QuestionTableModel;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class MainController implements Runnable {
    private final QuestionService service;
    private JFrame mainFrame;
    private JPanel mainPanel;

    public MainController(QuestionService service) {
        this.service = service;
    }

    @Override
    public void run() {
        mainFrame = new JFrame("Вопросница");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(300, 300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setJMenuBar(prepareMenu());
        prepareMainPanelForListQuestionCards();
        mainFrame.setVisible(true);
    }

    private void prepareMainPanelForAddQuestionCard(Optional<OpenQuestionCard> questionCardForEdit) {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new GridBagLayout());
        
        // Настройка поля ID
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("ID"), layoutConstraints);
        JTextField idField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        mainPanel.add(idField, layoutConstraints);

        // Настройка поля Вопрос
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Вопрос"), layoutConstraints);
        JTextField questionField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        mainPanel.add(questionField, layoutConstraints);

        // Настройка поля Ответ
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        mainPanel.add(new JLabel("Ответ"), layoutConstraints);
        JTextField expectedAnswerField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        mainPanel.add(expectedAnswerField, layoutConstraints);

        // Кнопка добавления вопроса
        JButton addButton = new JButton("Добавить вопрос");
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 3;
        layoutConstraints.gridwidth = 2;

        
        questionCardForEdit.ifPresent(q -> {
            idField.setText(String.valueOf(q.getId()));
            questionField.setText(q.getQuestion());
            expectedAnswerField.setText(q.getExpectedAnswer());
        });

        addButton.addActionListener(event -> {
            OpenQuestionCard questionCard = new OpenQuestionCard(
                    Long.valueOf(idField.getText()),
                    questionField.getText(),
                    expectedAnswerField.getText());
            service.save(questionCard);
            prepareMainPanelForListQuestionCards();
        });

        mainPanel.add(addButton, layoutConstraints);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private void prepareMainPanelForListQuestionCards() {
        if (mainPanel != null) {
            mainFrame.remove(mainPanel);
        }
        mainPanel = new JPanel(new BorderLayout());
        JTable table = new JTable(new QuestionTableModel(service.getAll()));
        mainPanel.add(table, BorderLayout.CENTER);
        mainPanel.add(table.getTableHeader(), BorderLayout.NORTH);
        mainFrame.add(mainPanel);
        SwingUtilities.updateComponentTreeUI(mainFrame);
    }

    private JMenuBar prepareMenu() {
        JMenu questionCardMenu = new JMenu("Вопросы");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(questionCardMenu);

        JMenuItem addQuestionCard = prepareAddMenuItem();
        questionCardMenu.add(addQuestionCard);

        JMenuItem listQuestionCards = prepareListMenuItem();
        questionCardMenu.add(listQuestionCards);

        JMenuItem editQuestionCard = prepareEditMenuItem();
        questionCardMenu.add(editQuestionCard);

        JMenuItem removeQuestionCard = prepareRemoveMenuItem();
        questionCardMenu.add(removeQuestionCard);

        return menuBar;
    }

    private JMenuItem prepareAddMenuItem() {
        JMenuItem addQuestionCard = new JMenuItem("Добавить вопрос");
        addQuestionCard.addActionListener((event) -> {
            prepareMainPanelForAddQuestionCard(Optional.empty());
        });
        return addQuestionCard;
    }

    private JMenuItem prepareListMenuItem() {
        JMenuItem listQuestionCards = new JMenuItem("Посмотреть вопросы");
        listQuestionCards.addActionListener((event) -> {
            prepareMainPanelForListQuestionCards();
        });
        return listQuestionCards;
    }

    private JMenuItem prepareRemoveMenuItem() {
        JMenuItem removeQuestionCard = new JMenuItem("Удалить вопрос");
        removeQuestionCard.addActionListener((event) -> {
            OpenQuestionCard questionCardToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос вы хотите удалить",
                    "Удаление вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            service.delete(questionCardToDelete.getId());
            prepareMainPanelForListQuestionCards();
        });
        return removeQuestionCard;
    }

    private JMenuItem prepareEditMenuItem() {
        JMenuItem editQuestionCard = new JMenuItem("Изменить вопрос");
        editQuestionCard.addActionListener((event) -> {
            OpenQuestionCard questionCardToEdit = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос вы хотите отредактировать?",
                    "Редактирование вопроса",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            prepareMainPanelForAddQuestionCard(Optional.of(questionCardToEdit));
        });
        return editQuestionCard;
    }
}

