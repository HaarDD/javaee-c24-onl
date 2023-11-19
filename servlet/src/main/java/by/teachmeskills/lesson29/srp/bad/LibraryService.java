package by.teachmeskills.lesson29.srp.bad;

import java.util.Arrays;
import java.util.List;

public class LibraryService {

    public static final List<Book> bookList = List.of(
            new Book("Книга №1"),
            new Book("Книга №2"),
            new Book("Книга №3"));

    //основная зона ответственности
    public Order orderBooks(Client client, Book... books) {
        return new Order(client, Arrays.stream(books).toList());
    }

    //зона ответственности №1
    public Book findBook(String name) {
        return bookList.stream().filter(book -> book.getName().equals(name)).findFirst().orElse(null);
    }

    //зона ответственности №2
    public void printOrder(Order order) {
        System.out.println(order);
    }

    //зона ответственности №3
    public void sendOrderClientNotification(Client client) {
        System.out.println("Письмо отправлено: " + client.getEmail());
    }

}
