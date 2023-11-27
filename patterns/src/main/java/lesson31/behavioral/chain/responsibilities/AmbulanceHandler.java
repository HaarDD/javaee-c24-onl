package lesson31.behavioral.chain.responsibilities;

import lesson31.behavioral.chain.managers.EmergencyServiceHandler;

public class AmbulanceHandler extends EmergencyServiceHandler {

    @Override
    public void handleEmergencyRequest() {
        System.out.print("Скорая помощь вызвана!");
        super.handleEmergencyRequest();
    }
}
