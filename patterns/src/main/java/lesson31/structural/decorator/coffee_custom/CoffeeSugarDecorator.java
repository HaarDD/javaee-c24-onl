package lesson31.structural.decorator.coffee_custom;

import lesson31.structural.decorator.coffee.Coffee;

public class CoffeeSugarDecorator implements Coffee {

    private final Coffee coffee;

    public CoffeeSugarDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", с сахаром";
    }

    @Override
    public double getCost() {
        return coffee.getCost();
    }
}
