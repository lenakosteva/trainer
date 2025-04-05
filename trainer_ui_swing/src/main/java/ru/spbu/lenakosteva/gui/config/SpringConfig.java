package ru.spbu.lenakosteva.gui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "ru.spbu.lenakosteva")
@PropertySource("jdbc.properties")
public class SpringConfig {

}
