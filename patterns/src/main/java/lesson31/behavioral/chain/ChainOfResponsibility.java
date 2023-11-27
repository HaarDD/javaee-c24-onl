package lesson31.behavioral.chain;

import lesson31.behavioral.chain.managers.EmergencyHandlerFactory;
import lesson31.behavioral.chain.managers.EmergencyType;

public class ChainOfResponsibility {

    public static void main(String[] args) {
        EmergencyHandlerFactory.createHandlerChain(EmergencyType.CRIME).handleEmergencyRequest();
        System.out.println("\n------");

        EmergencyHandlerFactory.createHandlerChain(EmergencyType.FIRE).handleEmergencyRequest();
        System.out.println("\n------");

        EmergencyHandlerFactory.createHandlerChain(EmergencyType.ROAD_ACCIDENT).handleEmergencyRequest();
        System.out.println("\n------");

    }
}
