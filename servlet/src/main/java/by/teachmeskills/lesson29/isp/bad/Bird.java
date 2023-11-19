package by.teachmeskills.lesson29.isp.bad;

public class Bird implements IBird {
    @Override
    public void fly() {
        System.out.println("Летит");
    }

    @Override
    public void eat() {
        System.out.println("Ест");
    }

    @Override
    public void tweet() {
        System.out.println("Чирикает");
    }
}
