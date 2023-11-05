package by.teachmeskills.lesson24;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = "/superheroes")
public class SuperHeroesServlet extends HttpServlet {

    public static List<SuperHeroCard> superHeroCardList = new ArrayList<>();

    {
        superHeroCardList = List.of(
                new SuperHeroCard("Человек-паук",
                        "spider-man.png",
                        2, 1, 1,
                        275, 286, 213, 207, 345,
                        "Питер Паркер, Человек-паук, подросток с паучьими способностями, защищает город в легендарном костюме с паутиной."),
                new SuperHeroCard("Халк",
                        "hulk.png",
                        3, 0, 3,
                        68, 499, 322, 122, 266,
                        "Сверхсильный Халк - зеленый гигант, превращающийся из ученого Брюса Баннера при волне гнева."),
                new SuperHeroCard("Железный человек",
                        "iron-man.png",
                        2, 0, 3,
                        388, 409, 354, 421, 213,
                        "Тони Старк, Железный человек, гений-изобретатель, использует высокотехнологичный бронекостюм для борьбы с преступностью и угрозами.")
        );
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("lesson24/superheroes.jsp").forward(request, response);
    }

}
