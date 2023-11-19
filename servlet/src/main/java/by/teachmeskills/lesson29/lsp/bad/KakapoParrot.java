package by.teachmeskills.lesson29.lsp.bad;

import by.teachmeskills.lesson29.isp.bad.IBird;

public class KakapoParrot extends Bird {
    @Override
    public void fly() throws RuntimeException {
        // Попугай Какапо не умеет летать, поэтому вызывается Exception
        throw new RuntimeException("Попугай Какапо не умеет летать!");
    }
}
