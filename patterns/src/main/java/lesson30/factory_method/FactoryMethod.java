package lesson30.factory_method;

import lesson30.factory_method.maker.ClayMaker;
import lesson30.factory_method.maker.GlassMaker;
import lesson30.factory_method.maker.Maker;

public class FactoryMethod {

    public static void main(String[] args) {

        Maker maker1 = new GlassMaker("Стекольный мастер");
        Maker maker2 = new ClayMaker("Глиняный мастер");
        System.out.println("maker1: " + maker1.make() + "\nmaker2: " + maker2.make());

    }


}
