package ru.spbu.lenakosteva.gui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.spbu.lenakosteva.domain.service.QuestionService;
import ru.spbu.lenakosteva.gui.config.SpringConfig;
import ru.spbu.lenakosteva.gui.controller.MainController;

import javax.swing.*;

public class Application {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        QuestionService taskService = context.getBean(QuestionService.class);
        SwingUtilities.invokeLater(new MainController(taskService));
    }
}