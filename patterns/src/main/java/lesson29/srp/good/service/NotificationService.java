package lesson29.srp.good.service;

import lesson29.srp.good.dto.Client;

public class NotificationService {

    public void sendOrderClientNotification(Client client) {
        System.out.println("Письмо отправлено: " + client.getEmail());
    }

}
