package ru.spbu.lenakosteva.controller;

import org.springframework.stereotype.Component;
import ru.spbu.lenakosteva.domain.model.FlashCard;
import ru.spbu.lenakosteva.domain.service.FlashCardService;

import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleController {
    private static final String MENU = """
          Введите [1], чтобы показать все задачи
          Введите [2], чтобы добавить задачу
          Введите [3], чтобы удалить задачу
          Введите [4], чтобы найти задачу
          Введите [5], чтобы выйти
          """;


    private final FlashCardService service;
    private final Scanner scanner;

    public ConsoleController(FlashCardService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void interactWithUser() {
        while (true) {
            printMenu();
            var option = scanner.nextLine();
            switch (option) {
                case "1" -> printAllCards();
                case "2" -> addNewCard();
                case "3" -> deleteCard();
                case "4" -> findCard();
                case "5" -> { return; }
                default -> System.out.println("Неизвестная команда");
            }
        }
    }

    private void printMenu() {
        System.out.print(MENU + "> ");
    }

    private void printAllCards() {
        service.getAll().forEach(System.out::println);
    }

    private void addNewCard() {
        var id = Long.parseLong(askUser("Введите id карточки (число)"));
        var question = askUser("Введите вопрос");
        var expectedAnswer = askUser("Введите ожидаемый ответ");
        var card = new FlashCard(id, question, expectedAnswer);

        service.save(card);
    }

    private void deleteCard() {
        var id = Long.parseLong(askUser("Введите id карточки (число)"));
        Optional<FlashCard> card = service.getById(id);
        if (card.isPresent()) {
            var confirmation = askUser("Введите [Y], если точно хотите удалить карточку " + card.get());
            if ("Y".equals(confirmation)) {
                service.delete(id);
            }
        } else {
            System.out.println("Такой карточки найти не удалось");
        }
    }

    private void findCard() {
        var id = Long.parseLong(askUser("Введите id карточки (число)"));
        Optional<FlashCard> card = service.getById(id);

        if (card.isPresent()) {
            System.out.println(card);
        } else {
            System.out.println("Карточка с таким id не найдена :(");
        }
    }

    private String askUser(String message) {
        System.out.print(message + ": \n> ");
        return scanner.nextLine();
    }
}
