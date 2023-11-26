package lesson29.srp.good.service;

import lesson29.srp.good.dto.Book;

import java.util.List;

public class BookService {

    //список для примера
    public static final List<Book> bookList = List.of(
            new Book("Книга №1"),
            new Book("Книга №2"),
            new Book("Книга №3"));

    public Book findBook(String name) {
        return bookList.stream().filter(book -> book.getName().equals(name)).findFirst().orElse(null);
    }
}
