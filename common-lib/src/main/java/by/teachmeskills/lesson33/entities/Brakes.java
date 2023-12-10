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
public class Brakes implements Cloneable {
    private String brakeType;
    private boolean hasABS;

    @Override
    public Brakes clone() {
        try {
            return (Brakes) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
