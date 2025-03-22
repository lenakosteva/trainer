package ru.spbu.lenakosteva.gui.model;

import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class QuestionTableModel extends AbstractTableModel {

    private final String[] columns = new String[]{ "ID", "Вопрос", "Ответ" };
    private final List<OpenQuestionCard> questions;

    public QuestionTableModel(List<OpenQuestionCard> questions) {
        this.questions = questions;
    }

    @Override
    public int getRowCount() {
        return questions.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OpenQuestionCard questionCard = questions.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> questions.get(rowIndex).getId();
            case 1 -> questions.get(rowIndex).getQuestion();
            case 2 -> questions.get(rowIndex).getExpectedAnswer();
            default -> throw new IllegalArgumentException("Номер колонки больше возможного или отрицательный");
        };
    }
}
