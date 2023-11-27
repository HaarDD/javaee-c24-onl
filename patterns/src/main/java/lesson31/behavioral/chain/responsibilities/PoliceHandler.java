package lesson31.behavioral.chain.responsibilities;

import lesson31.behavioral.chain.managers.EmergencyServiceHandler;

public class PoliceHandler extends EmergencyServiceHandler {
    @Override
    public void handleEmergencyRequest() {
        System.out.print("Полиция вызвана!");
        super.handleEmergencyRequest();
    }
}
