package lesson31.behavioral.chain.responsibilities;

import lesson31.behavioral.chain.managers.EmergencyServiceHandler;

public class FireDepartmentHandler extends EmergencyServiceHandler {
    @Override
    public void handleEmergencyRequest() {
        System.out.print("Пожарная служба вызвана!");
        super.handleEmergencyRequest();
    }
}
