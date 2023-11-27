package lesson31.structural.decorator.coffee_custom;

import lesson31.structural.decorator.coffee.Coffee;

public class CoffeeMilkDecorator implements Coffee {
    private final Coffee coffee;

    public CoffeeMilkDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getDescription() {
        return coffee.getDescription() + ", с молоком";
    }

    @Override
    public double getCost() {
        return coffee.getCost() + 0.5;
    }
}
