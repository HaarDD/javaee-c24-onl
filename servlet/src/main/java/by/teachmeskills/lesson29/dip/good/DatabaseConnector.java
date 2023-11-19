package by.teachmeskills.lesson29.dip.good;

public class DatabaseConnector {

    //теперь класс не зависит от какой-либо реализации БД
    private DatabaseConnection databaseConnection;

    public DatabaseConnector(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public void connect() {
        databaseConnection.connect();
    }

    public void disconnect() {
        databaseConnection.disconnect();
    }

}
