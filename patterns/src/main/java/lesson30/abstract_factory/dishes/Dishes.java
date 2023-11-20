package lesson30.abstract_factory.dishes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Dishes {

    private String name;

    public String toString() {
        return "class: " + this.getClass().getSimpleName() + " " + this.name;
    }
}
