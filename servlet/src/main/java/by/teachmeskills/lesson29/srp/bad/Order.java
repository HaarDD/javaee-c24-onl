package by.teachmeskills.lesson29.srp.bad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
public class Order {

    private Client client;
    private List<Book> bookList;

    public String toString() {
        return "Order(client=" + this.getClient() + ", bookList=" + this.getBookList().stream().map(Object::toString).collect(Collectors.joining(", ")) + ")";
    }
}
