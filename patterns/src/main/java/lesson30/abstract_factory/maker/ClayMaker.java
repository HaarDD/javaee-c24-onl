package lesson30.abstract_factory.maker;

import lesson30.abstract_factory.dishes.ClayDishes;
import lesson30.abstract_factory.dishes.Dishes;

public class ClayMaker implements Maker {
    @Override
    public Dishes make() {
        return new ClayDishes();
    }
}
