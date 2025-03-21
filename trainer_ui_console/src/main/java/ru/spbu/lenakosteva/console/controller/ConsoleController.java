package ru.spbu.lenakosteva.console.controller;

import org.springframework.stereotype.Component;
import ru.spbu.lenakosteva.domain.model.OpenQuestionCard;
import ru.spbu.lenakosteva.domain.service.QuestionService;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {
    private static final String MENU = """
          Введите [1], чтобы показать все вопросы
          Введите [2], чтобы добавить вопрос
          Введите [3], чтобы удалить вопрос
          Введите [4], чтобы найти вопрос
          Введите [5], чтобы выйти
          """;


    private final QuestionService service;
    private final Scanner scanner;

    public ConsoleController(QuestionService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void interactWithUser() {
        while (true) {
            printMenu();
            var option = scanner.nextLine();
            switch (option) {
                case "1" -> printAllQuestions();
                case "2" -> addNewQuestion();
                case "3" -> deleteQuestion();
                case "4" -> findQuestion();
                case "5" -> { return; }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu() {
        System.out.print(MENU + "> ");
    }

    private void printAllQuestions() {
        service.getAll().forEach(System.out::println);
    }

    private void addNewQuestion() {
        var id = Long.parseLong(askUser("Введите id вопроса (число)"));
        var question = askUser("Введите вопрос");
        var expectedAnswer = askUser("Введите ожидаемый ответ");
        var questionCard = new OpenQuestionCard(id, question, expectedAnswer);

        service.save(questionCard);
    }

    private void deleteQuestion() {
        var id = Long.parseLong(askUser("Введите id вопроса (число)"));
        Optional<OpenQuestionCard> questionCard = service.getById(id);
        if (questionCard.isPresent()) {
            var confirmation = askUser("Введите [Y], если точно хотите удалить вопрос " + questionCard.get());
            if ("Y".equals(confirmation)) {
                service.delete(id);
            }
        } else {
            System.out.println("Такой вопрос найти не удалось");
        }
    }

    private void findQuestion() {
        var id = Long.parseLong(askUser("Введите id вопроса (число)"));
        Optional<OpenQuestionCard> questionCard = service.getById(id);

        if (questionCard.isPresent()) {
            System.out.println(questionCard.get());
        } else {
            System.out.println("Вопрос с таким id не найден :(");
        }
    }

    private String askUser(String message) {
        System.out.print(message + ": \n> ");
        return scanner.nextLine();
    }
}
