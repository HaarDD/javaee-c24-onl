package by.teachmeskills.lesson41.controller.mvc;

import by.teachmeskills.lesson41.dao.AuthorsRepository;
import by.teachmeskills.lesson41.dto.AuthorDto;
import by.teachmeskills.lesson41.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/authors")
public class AuthorsMvcController {

    private final AuthorsRepository authorsRepository;

    @GetMapping
    public String getAllAuthors(Model model) {



        List<AuthorDto> authorDtoList = authorsRepository.getAllAuthors();
        if (authorDtoList.isEmpty()) {
            throw new ResourceNotFoundException("Автор(ы) не найден(ы)!");
        }
        model.addAttribute("authors",authorDtoList);
        return "author";
    }

}

