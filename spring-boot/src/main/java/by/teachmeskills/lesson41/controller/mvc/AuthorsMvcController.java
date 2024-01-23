package by.teachmeskills.lesson41.controller.mvc;

import by.teachmeskills.lesson41.entity.Author;
import by.teachmeskills.lesson41.service.AuthorsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorsMvcController {

    private final AuthorsService authorsService;

    @GetMapping
    public String getAllAuthors(Model model) {
        List<Author> authorList = authorsService.getAllAuthors();
        model.addAttribute("authors", authorList);
        return "author";
    }

}

