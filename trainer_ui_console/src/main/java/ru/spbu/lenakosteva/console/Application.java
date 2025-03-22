package ru.spbu.lenakosteva.console;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.spbu.lenakosteva.console.config.SpringConfig;
import ru.spbu.lenakosteva.console.controller.ConsoleController;

public class Application {
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        ConsoleController controller = context.getBean(ConsoleController.class);
        controller.interactWithUser();
    }
}
