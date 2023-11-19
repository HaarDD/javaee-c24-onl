package lesson29.ocp.bad.service;

import lesson29.ocp.bad.dto.Order;

public class NotificationService {

    public static final int NOTIFICATION_TYPE_EMAIL = 1;
    public static final int NOTIFICATION_TYPE_SMS = 2;

    public void sendOrderClientNotification(Order order, int notificationType) {
        if (notificationType == NOTIFICATION_TYPE_EMAIL) {
            System.out.println("Письмо отправлено: " + order.getClient().getEmail() + " " + order);
        } else if (notificationType == NOTIFICATION_TYPE_SMS) {
            System.out.println("СМС отправлено: " + order.getClient().getPhoneNumber() + " " + order);
        }
    }

}
