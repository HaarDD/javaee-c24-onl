package by.teachmeskills.lesson39.controller;

import by.teachmeskills.lesson39.dao.AuthorDao;
import by.teachmeskills.lesson39.dao.BookDao;
import by.teachmeskills.lesson39.dto.AuthorDto;
import by.teachmeskills.lesson39.dto.BookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class LibraryController {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    @RequestMapping("/books")
    public String showBooks(Model model) {
        model.addAttribute("books", BookDao.getAllBooks());
        List<AuthorDto> authors = AuthorDao.getAllAuthors();
        model.addAttribute("authors", authors);
        return "books";
    }

    @GetMapping("/get_book_info")
    @ResponseBody
    public ResponseEntity<BookDto> getBookInfoById(@RequestParam Long id) {
        BookDto bookDto = BookDao.getBookById(id);

        if (bookDto != null) {
            return new ResponseEntity<>(bookDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/add_book", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> addBook(@ModelAttribute @Valid BookDto bookDto) {
        BookDao.addBook(bookDto);
        return ResponseEntity.ok(Collections.emptyMap());
    }

    @PostMapping(value = "/edit_book", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ResponseEntity<?> editBook(@ModelAttribute @Valid BookDto bookDto) {
        BookDao.editBook(bookDto);
        return ResponseEntity.ok(Collections.emptyMap());
    }

    @DeleteMapping("/delete_book")
    public ResponseEntity<String> deleteBook(@RequestParam Long id) {
        BookDao.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/edit_book")
    public String showEditBookForm(@RequestParam Long id, Model model) {
        BookDto book = BookDao.getBookById(id);
        model.addAttribute("bookDto", book);
        List<AuthorDto> authors = AuthorDao.getAllAuthors();
        model.addAttribute("authors", authors);
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