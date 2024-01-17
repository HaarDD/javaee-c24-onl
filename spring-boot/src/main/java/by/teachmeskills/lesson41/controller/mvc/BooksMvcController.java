package by.teachmeskills.lesson41.controller.mvc;

import by.teachmeskills.lesson41.entity.Book;
import by.teachmeskills.lesson41.service.AuthorsService;
import by.teachmeskills.lesson41.service.BooksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/books")
public class BooksMvcController {

    private final BooksService booksService;

    private final AuthorsService authorsService;

    @GetMapping
    public String showBooks(Model model, @RequestParam(required = false) String searchText,
                            @RequestParam(required = false) String searchType,
                            @RequestParam(required = false) List<Integer> authorSelect,
                            @RequestParam(required = false) Integer pagesFrom,
                            @RequestParam(required = false) Integer pagesTo) {
        log.info("Параметры фильтрации: searchText: {},searchType: {}, authorSelect: {}, pagesFrom: {}, pagesTo: {}", searchText, searchType, authorSelect, pagesFrom, pagesTo);

        List<Book> bookList = booksService.getFilteredBooks(searchText, searchType, authorSelect, pagesFrom, pagesTo);

        model.addAttribute("books", bookList);
        model.addAttribute("authors", authorsService.getAllAuthors());
        model.addAttribute("searchText", searchText);
        model.addAttribute("searchType", searchType);
        model.addAttribute("authorSelect", authorSelect);
        model.addAttribute("pagesFrom", pagesFrom);
        model.addAttribute("pagesTo", pagesTo);
        return "books";
    }
}
