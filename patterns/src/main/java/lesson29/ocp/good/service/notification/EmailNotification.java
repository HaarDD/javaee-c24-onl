package lesson29.ocp.good.service.notification;

public class EmailNotification implements Notification {
    @Override
    public void sendNotification(String address, String message) {
        System.out.println("Письмо отправлено: " + address + " " + message);
    }
}