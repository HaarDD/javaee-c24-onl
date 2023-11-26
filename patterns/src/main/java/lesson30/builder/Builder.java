package lesson30.builder;

public class Builder {

    public static void main(String[] args) {
        Book book = new Book.BookBuilder()
                .title("Ведьмак: последнее желание")
                .author("Анджей Сапковский")
                .pageCount(640)
                .genre("Фэнтези")
                .publisher("superNOWA")
                .publicationYear(1993)
                .language("Польский")
                .build();
        System.out.println(book);
    }
}
