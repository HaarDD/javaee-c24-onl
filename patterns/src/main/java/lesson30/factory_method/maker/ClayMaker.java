package lesson30.factory_method.maker;

import lesson30.factory_method.dishes.ClayDishes;
import lesson30.factory_method.dishes.Dishes;

public class ClayMaker extends Maker {

    public ClayMaker(String name) {
        super(name);
    }

    @Override
    public Dishes make() {
        return new ClayDishes();
    }
}
