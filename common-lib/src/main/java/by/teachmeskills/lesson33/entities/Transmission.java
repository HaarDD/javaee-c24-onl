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
public class Transmission implements Cloneable {
    private String transmissionType;
    private int numberOfGears;

    @Override
    public Transmission clone() {
        try {
            return (Transmission) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
