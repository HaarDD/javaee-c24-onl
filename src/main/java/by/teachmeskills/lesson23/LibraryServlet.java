package by.teachmeskills.lesson23;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = {"/library"})
public class LibraryServlet extends HttpServlet {

    public static final String BOOK_NAME_PARAMETER = "book";

    public static final String BOOK_STORAGE_PATH = "storage/books/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/library.jsp").forward(request, response);
    }

}
