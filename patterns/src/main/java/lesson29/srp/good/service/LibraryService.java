package lesson29.srp.good.service;

import lesson29.srp.good.dto.Book;
import lesson29.srp.good.dto.Client;
import lesson29.srp.good.dto.Order;

import java.util.Arrays;

public class LibraryService {

    //основная зона ответственности
    public Order orderBooks(Client client, Book... books) {
        return new Order(client, Arrays.stream(books).toList());
    }

}
