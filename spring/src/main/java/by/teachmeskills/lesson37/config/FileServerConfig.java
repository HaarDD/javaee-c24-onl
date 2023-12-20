package by.teachmeskills.lesson37.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"by.teachmeskills.lesson37.auth", "by.teachmeskills.lesson37.file"})
public class FileServerConfig {

    @Bean
    String appName() {
        return "TMS. Домашнее задание №37";
    }

}
