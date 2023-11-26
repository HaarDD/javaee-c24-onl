package lesson30.abstract_factory.maker;

import lesson30.abstract_factory.dishes.Dishes;
import lesson30.abstract_factory.dishes.GlassDishes;

public class GlassMaker implements Maker {
    @Override
    public Dishes make() {
        return new GlassDishes();
    }
}
