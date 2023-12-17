package by.teachmeskills.lesson33;

import by.teachmeskills.lesson33.entities.*;

public class ObjectMethods {

    public static void main(String[] args) {
        Engine engine = Engine.builder()
                .model("5-цилиндровый")
                .displacement(1500)
                .cylinders(5)
                .build();

        Brakes brakes = Brakes.builder()
                .brakeType("Диск")
                .hasABS(true)
                .build();

        Transmission transmission = Transmission.builder()
                .transmissionType("Ручная коробка")
                .numberOfGears(6)
                .build();

        Wheels wheels = Wheels.builder()
                .sizeInInches(17)
                .material("Металл")
                .build();

        Motorcycle motorcycle = Motorcycle.builder()
                .brand("Harley-Davidson")
                .year(2022)
                .engine(engine)
                .brakes(brakes)
                .transmission(transmission)
                .wheels(wheels)
                .build();

        System.out.println("Созданный мотоцикл: " + motorcycle);

        Motorcycle clonedMotorcycle = motorcycle.clone();

        System.out.println("Скопированный мотоцикл: " + clonedMotorcycle);

        System.out.println("equals: " + motorcycle.equals(clonedMotorcycle));

        System.out.println("Хэш-код оригинала: " + motorcycle.hashCode());
        System.out.println("Хэш-код клона: " + clonedMotorcycle.hashCode());

        clonedMotorcycle.setYear(2010);

        System.out.println("Хэш-код оригинала: " + motorcycle.hashCode());
        System.out.println("Хэш-код клона: " + clonedMotorcycle.hashCode());
    }

}
