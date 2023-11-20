package lesson30.abstract_factory.maker;

import lesson30.abstract_factory.dishes.Dishes;
import lombok.AllArgsConstructor;

public interface Maker {
    Dishes make();

}
