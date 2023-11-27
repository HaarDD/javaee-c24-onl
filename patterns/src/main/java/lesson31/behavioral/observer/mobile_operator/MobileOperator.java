package lesson31.behavioral.observer.mobile_operator;

import java.util.ArrayList;
import java.util.List;

public class MobileOperator implements Observable {
    private final List<Observer> clients = new ArrayList<>();
    private String message;

    public void setMessage(String message) {
        this.message = message;
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer observer) {
        clients.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        clients.remove(observer);
    }

    @Override
    public void notifyObservers() {
        clients.forEach(client -> client.send(message));
    }
}
