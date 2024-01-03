package by.teachmeskills.lesson39.dbservice;

import lombok.Data;

@Data
public class DatabaseConfig {
    private String url;
    private String user;
    private String password;
    private String databaseName;
    private String driverClassName;
}
