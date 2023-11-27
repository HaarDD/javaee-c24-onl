package lesson31.structural.decorator.coffee;

public class CoffeeBase implements Coffee {
    @Override
    public String getDescription() {
        return "Американо";
    }

    @Override
    public double getCost() {
        return 3.0;
    }

}
