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
        GridBagConstraints layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 0;
        mainPanel.add(new JLabel("ID"), layoutConstraints);
        JTextField idField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 0;
        mainPanel.add(idField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 1;
        mainPanel.add(new JLabel("Вопрос"), layoutConstraints);
        JTextField questionField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 1;
        mainPanel.add(questionField, layoutConstraints);

        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = 2;
        mainPanel.add(new JLabel("Ответ"), layoutConstraints);
        JTextField expectedAnswerField = new JTextField(15);
        layoutConstraints = new GridBagConstraints();
        layoutConstraints.gridx = 1;
        layoutConstraints.gridy = 2;
        mainPanel.add(expectedAnswerField, layoutConstraints);

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
        JMenuBar menuBar = new JMenuBar();
        JMenu questionCardMenu = new JMenu("Вопросы");
        JMenuItem addQuestionCard = new JMenuItem("Добавить вопрос");
        addQuestionCard.addActionListener((event) -> {
            prepareMainPanelForAddQuestionCard(Optional.empty());
        });
        questionCardMenu.add(addQuestionCard);
        menuBar.add(questionCardMenu);

        JMenuItem listQuestionCards = new JMenuItem("Посмотреть вопросы");
        listQuestionCards.addActionListener((event) -> {
            prepareMainPanelForListQuestionCards();
        });
        questionCardMenu.add(listQuestionCards);

        JMenuItem removeQuestionCard = new JMenuItem("Удалить вопрос");
        removeQuestionCard.addActionListener((event) -> {
            OpenQuestionCard questionCardToDelete = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос вы хотите удалить",
                    "Удалить вопрос",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            service.delete(questionCardToDelete.getId());
            prepareMainPanelForListQuestionCards();
        });
        questionCardMenu.add(removeQuestionCard);

        JMenuItem editQuestionCard = new JMenuItem("Отредактировать вопрос");
        editQuestionCard.addActionListener((event) -> {
            OpenQuestionCard questionCardToEdit = (OpenQuestionCard) JOptionPane.showInputDialog(
                    mainFrame,
                    "Какой вопрос вы хотите отредактировать?",
                    "Редактировать вопрос",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    service.getAll().toArray(),
                    null);
            prepareMainPanelForAddQuestionCard(Optional.of(questionCardToEdit));
        });
        questionCardMenu.add(editQuestionCard);

        return menuBar;
    }
}

