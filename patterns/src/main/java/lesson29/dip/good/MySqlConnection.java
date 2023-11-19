package lesson29.dip.good;

public class MySqlConnection implements DatabaseConnection {

    @Override
    public void connect() {
        System.out.println("Подключено к MySQL");
    }

    @Override
    public void disconnect() {
        System.out.println("Отключено от MySQL");
    }
}
