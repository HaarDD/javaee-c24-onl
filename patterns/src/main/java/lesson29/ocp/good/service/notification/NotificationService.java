package lesson29.ocp.good.service.notification;

public class NotificationService {

    public void sendOrderClientNotification(Notification notification, String address, String message) {
        notification.sendNotification(address, message);
    }

}
