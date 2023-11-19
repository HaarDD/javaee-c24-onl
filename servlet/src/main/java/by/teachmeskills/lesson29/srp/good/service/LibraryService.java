package by.teachmeskills.lesson29.srp.good.service;

import by.teachmeskills.lesson29.srp.good.dto.Book;
import by.teachmeskills.lesson29.srp.good.dto.Client;
import by.teachmeskills.lesson29.srp.good.dto.Order;

import java.util.Arrays;
import java.util.List;

public class LibraryService {

    //основная зона ответственности
    public Order orderBooks(Client client, Book... books) {
        return new Order(client, Arrays.stream(books).toList());
    }

}
