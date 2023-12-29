package by.teachmeskills.lesson39.controller;

import by.teachmeskills.lesson39.dao.AuthorDao;
import by.teachmeskills.lesson39.dao.BookDao;
import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class BookstoreController {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @RequestMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", BookDao.getAllBooks());
        List<AuthorDto> authors = AuthorDao.getAllAuthors(); // Замените на ваш сервис для получения авторов
        model.addAttribute("authors", authors);
        return "books";
    }

    @RequestMapping("/add_book")
    public String addBook(Model model) {
        List<AuthorDto> authors = AuthorDao.getAllAuthors(); // Замените на ваш сервис для получения авторов
        model.addAttribute("authors", authors);
        return "add_book";
    }

    @GetMapping("/add_book")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDto", new BookDto()); // Пустой объект BookDto для формы добавления
        List<AuthorDto> authors = AuthorDao.getAllAuthors(); // Замените на ваш сервис для получения авторов
        model.addAttribute("authors", authors);
        return "add_book";
    }

    @PostMapping(value = "/add_book", produces = "text/html;charset=UTF-8")
    public String addBook(@ModelAttribute BookDto bookDto, @RequestParam List<Long> authorIds) {

        if (authorIds == null) {
            BookDao.addBook(bookDto);
            log.info("Добавлена книга без автора: {}", bookDto);
        } else {
            BookDao.addBook(bookDto, authorIds);
        }

        log.info("book: {}", bookDto);
        log.info("authorIds: {}", authorIds);

        // Логика добавления книги в базу данных

        return "redirect:/books";
    }

    @PostMapping(value = "/add_author", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addAuthor(@RequestParam("name") String name) {
        Map<String, Object> response = new HashMap<>();

        Long authorId = AuthorDao.addAuthor(name);

        if (authorId != null) {
            response.put("success", true);
            response.put("authorId", authorId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("success", false);
            response.put("message", "Failed to add author.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
