package lesson30.builder;

import lombok.ToString;

@ToString
public class Book {

    private String title;
    private String author;
    private int pageCount;
    private String genre;
    private String publisher;
    private int publicationYear;
    private String language;

    Book(String title, String author, int pageCount, String genre, String publisher, int publicationYear, String language) {
        this.title = title;
        this.author = author;
        this.pageCount = pageCount;
        this.genre = genre;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.language = language;
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public static class BookBuilder {
        private String title;
        private String author;
        private int pageCount;
        private String genre;
        private String publisher;
        private int publicationYear;
        private String language;

        BookBuilder() {
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }

        public BookBuilder genre(String genre) {
            this.genre = genre;
            return this;
        }

        public BookBuilder publisher(String publisher) {
            this.publisher = publisher;
            return this;
        }

        public BookBuilder publicationYear(int publicationYear) {
            this.publicationYear = publicationYear;
            return this;
        }

        public BookBuilder language(String language) {
            this.language = language;
            return this;
        }

        public Book build() {
            return new Book(this.title, this.author, this.pageCount, this.genre, this.publisher, this.publicationYear, this.language);
        }

        public String toString() {
            return "Book.BookBuilder(title=" + this.title + ", author=" + this.author + ", pageCount=" + this.pageCount + ", genre=" + this.genre + ", publisher=" + this.publisher + ", publicationYear=" + this.publicationYear + ", language=" + this.language + ")";
        }
    }
}
