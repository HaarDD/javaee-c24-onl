package by.teachmeskills.lesson41.controller.mvc;


import by.teachmeskills.lesson41.dao.BooksRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksMvcController {

    private final BooksRepository booksRepository;
    @GetMapping
    public String showBooks(Model model) {
        model.addAttribute("books", booksRepository.getAllBooks());
        return "books";
    }
}
