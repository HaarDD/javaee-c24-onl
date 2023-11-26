package lesson30.factory_method.dishes;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Dishes {

    private String name;

    public String toString() {
        return "class: " + this.getClass().getSimpleName() + " " + this.name;
    }
}
