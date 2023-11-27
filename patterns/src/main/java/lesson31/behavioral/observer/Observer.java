package lesson31.behavioral.observer;

import lesson31.behavioral.observer.mobile_operator.Client;
import lesson31.behavioral.observer.mobile_operator.MobileOperator;

public class Observer {

    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();

        new Client("Максим", mobileOperator);
        new Client("Артем", mobileOperator);

        mobileOperator.setMessage("Внимание, ожидается метель в 14:00!");
    }

}
