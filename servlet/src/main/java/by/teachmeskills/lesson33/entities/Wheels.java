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
public class Wheels implements Cloneable {
    private int sizeInInches;
    private String material;

    @Override
    public Wheels clone() {
        try {
            return (Wheels) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

