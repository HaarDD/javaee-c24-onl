package by.teachmeskills.lesson33.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Motorcycle implements Cloneable {
    private String brand;
    private int year;
    private Engine engine;
    private Brakes brakes;
    private Transmission transmission;
    private Wheels wheels;

    @Override
    public Motorcycle clone() {
        try {
            Motorcycle clonedMotorcycle = (Motorcycle) super.clone();
            clonedMotorcycle.setEngine(engine.clone());
            clonedMotorcycle.setBrakes(brakes.clone());
            clonedMotorcycle.setTransmission(transmission.clone());
            clonedMotorcycle.setWheels(wheels.clone());
            return clonedMotorcycle;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
