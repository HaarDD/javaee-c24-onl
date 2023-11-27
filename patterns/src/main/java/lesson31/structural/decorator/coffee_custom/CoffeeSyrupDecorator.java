package lesson31.structural.decorator.coffee_custom;

import lesson31.structural.decorator.coffee.Coffee;

public class CoffeeSyrupDecorator implements Coffee {

    private final Coffee coffee;

    public CoffeeSyrupDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", с сиропом";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 1.0;
    }
}
