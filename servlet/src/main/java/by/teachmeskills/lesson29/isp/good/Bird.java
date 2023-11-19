package by.teachmeskills.lesson29.isp.good;

public class Bird implements IEat, ITweet, IFly {

    @Override
    public void eat() {
        System.out.println("Ест");
    }

    @Override
    public void tweet() {
        System.out.println("Чирикает");
    }

    @Override
    public void fly() {
        System.out.println("Летит");
    }
}
