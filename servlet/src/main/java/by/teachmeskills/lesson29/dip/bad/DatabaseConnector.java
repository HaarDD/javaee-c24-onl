package by.teachmeskills.lesson29.dip.bad;

public class DatabaseConnector {

    //класс зависит от конкретной реализации БД
    private MySqlConnection mySqlConnection;

    public DatabaseConnector() {
        this.mySqlConnection = new MySqlConnection();
    }

    public void connect() {
        mySqlConnection.connect();
    }

    public void disconnect() {
        mySqlConnection.disconnect();
    }

}
