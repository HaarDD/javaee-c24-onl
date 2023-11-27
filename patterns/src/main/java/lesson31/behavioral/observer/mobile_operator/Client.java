package lesson31.behavioral.observer.mobile_operator;

import java.util.Objects;

public class Client implements Observer {
    private String name;

    public Client(String name, Observable observable) {
        this.name = name;
        observable.registerObserver(this);
    }

    @Override
    public void send(String message) {
        System.out.printf("Клиенту \"%s\" пришло сообщение: \"%s\"\n", this.name, message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(name, client.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
