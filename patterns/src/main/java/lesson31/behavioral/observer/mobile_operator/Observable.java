package lesson31.behavioral.observer.mobile_operator;

public interface Observable {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();

}
