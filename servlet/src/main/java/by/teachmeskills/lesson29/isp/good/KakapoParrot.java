package by.teachmeskills.lesson29.isp.good;

public class KakapoParrot implements IEat, ITweet {

    @Override
    public void eat() {
        System.out.println("Ест");
    }

    @Override
    public void tweet() {
        System.out.println("Чирикает");
    }

}
