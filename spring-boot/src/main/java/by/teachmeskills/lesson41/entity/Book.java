package by.teachmeskills.lesson41.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.ISBN;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false, length = 255)
    @NotNull
    @NotEmpty
    @Size(max = 150, min = 2)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT", length = 10000)
    @Size(max = 10000)
    private String description;

    @Column(name = "isbn")
    @ISBN
    private String isbn;

    @Column(name = "pages")
    @Min(1)
    @Max(5000)
    private Integer pages;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "bookid"),
            inverseJoinColumns = @JoinColumn(name = "authorid")
    )
    @Cascade(CascadeType.ALL)
    private List<Author> authors;
}
