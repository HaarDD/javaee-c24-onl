package lesson30.factory_method.maker;

import lesson30.factory_method.dishes.Dishes;
import lesson30.factory_method.dishes.GlassDishes;

public class GlassMaker extends Maker {

    public GlassMaker(String name) {
        super(name);
    }

    @Override
    public Dishes make() {
        return new GlassDishes();
    }
}
