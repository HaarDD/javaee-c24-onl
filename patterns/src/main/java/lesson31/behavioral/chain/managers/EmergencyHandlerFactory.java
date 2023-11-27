package lesson31.behavioral.chain.managers;

import lesson31.behavioral.chain.responsibilities.AmbulanceHandler;
import lesson31.behavioral.chain.responsibilities.FireDepartmentHandler;
import lesson31.behavioral.chain.responsibilities.PoliceHandler;

import java.util.Map;

public class EmergencyHandlerFactory {

    //Установка порядка для каждого типа происшествия
    public static final Map<EmergencyType, EmergencyServiceHandler[]> emergencyServicesOrderByType = Map.of(
            EmergencyType.CRIME, new EmergencyServiceHandler[]{new PoliceHandler(), new AmbulanceHandler()},
            EmergencyType.FIRE, new EmergencyServiceHandler[]{new FireDepartmentHandler(), new AmbulanceHandler()},
            EmergencyType.ROAD_ACCIDENT, new EmergencyServiceHandler[]{new AmbulanceHandler(), new PoliceHandler(), new FireDepartmentHandler()}
    );

    public static EmergencyServiceHandler createHandlerChain(EmergencyType emergencyType) {
        System.out.println("Тип происшествия: " + emergencyType.name());

        EmergencyServiceHandler[] handlers = emergencyServicesOrderByType.getOrDefault(emergencyType, new EmergencyServiceHandler[]{new PoliceHandler()});
        //Преобразования порядка массива в цепочку
        for (int i = 0; i < handlers.length - 1; i++) {
            handlers[i].setNextServiceHandler(handlers[i + 1]);
        }
        //Возврат первого в цепочке
        return handlers[0];
    }
}