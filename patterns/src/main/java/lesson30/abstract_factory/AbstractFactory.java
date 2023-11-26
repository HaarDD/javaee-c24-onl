package lesson30.abstract_factory;

import lesson30.abstract_factory.maker.ClayMaker;
import lesson30.abstract_factory.maker.GlassMaker;
import lesson30.abstract_factory.maker.Maker;

public class AbstractFactory {

    public static void main(String[] args) {
        Maker maker1 = new GlassMaker();
        Maker maker2 = new ClayMaker();
        System.out.println("maker1: " + maker1.make() + "\nmaker2: " + maker2.make());
    }


}
