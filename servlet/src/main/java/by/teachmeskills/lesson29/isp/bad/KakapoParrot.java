package by.teachmeskills.lesson29.isp.bad;

public class KakapoParrot implements IBird {

    @Override
    public void eat() {
        System.out.println("Ест");
    }

    @Override
    public void tweet() {
        System.out.println("Чирикает");
    }

    @Override
    public void fly() throws RuntimeException {
        // Попугай Какапо не умеет летать, поэтому вызывается Exception
        throw new RuntimeException("Попугай Какапо не умеет летать!");
    }
}
