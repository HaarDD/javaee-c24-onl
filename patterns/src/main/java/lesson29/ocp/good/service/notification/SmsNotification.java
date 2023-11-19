package lesson29.ocp.good.service.notification;

public class SmsNotification implements Notification {
    @Override
    public void sendNotification(String address, String message) {
        System.out.println("СМС отправлено: " + address + " " + message);
    }
}
