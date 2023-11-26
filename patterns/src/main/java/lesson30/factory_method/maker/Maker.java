package lesson30.factory_method.maker;

import lesson30.factory_method.dishes.Dishes;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class Maker {

    private String name;

    abstract public Dishes make();

}
