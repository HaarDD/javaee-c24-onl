package lesson31.behavioral.chain.managers;

import lombok.AccessLevel;
import lombok.Setter;

public abstract class EmergencyServiceHandler {
    @Setter(AccessLevel.PUBLIC)
    protected EmergencyServiceHandler nextServiceHandler;

    public void handleEmergencyRequest() {
        if (nextServiceHandler != null) {
            System.out.print(" -> ");
            nextServiceHandler.handleEmergencyRequest();
        }
    }
}
