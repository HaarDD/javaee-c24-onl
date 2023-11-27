package lesson31.structural.decorator;

import lesson31.structural.decorator.coffee.Coffee;
import lesson31.structural.decorator.coffee.CoffeeBase;
import lesson31.structural.decorator.coffee_custom.CoffeeMilkDecorator;
import lesson31.structural.decorator.coffee_custom.CoffeeSugarDecorator;
import lesson31.structural.decorator.coffee_custom.CoffeeSyrupDecorator;

public class Decorator {

    public static void main(String[] args) {
        Coffee coffee = new CoffeeBase();
        printCoffee(coffee);
        //Добавляем сахар к кофе
        Coffee coffeeSugar = new CoffeeSugarDecorator(coffee);
        printCoffee(coffeeSugar);
        //Добавляем молоко к кофе с сахаром
        Coffee coffeeMilk = new CoffeeMilkDecorator(coffeeSugar);
        printCoffee(coffeeMilk);
        //Добавляем сироп к кофе с молоком и сахаром
        Coffee coffeeSyrup = new CoffeeSyrupDecorator(coffeeMilk);
        printCoffee(coffeeSyrup);
    }

    private static void printCoffee(Coffee coffee) {
        System.out.printf("Описание: %s\nЦена: %,.1f ₽\n", coffee.getDescription(), coffee.getCost());
    }

}
