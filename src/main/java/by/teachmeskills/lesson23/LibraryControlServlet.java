package by.teachmeskills.lesson23;

import by.teachmeskills.lesson23.utils.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static by.teachmeskills.lesson23.LibraryServlet.BOOK_NAME_PARAMETER;
import static by.teachmeskills.lesson23.LibraryServlet.BOOK_STORAGE_PATH;

@WebServlet(urlPatterns = {"/library/control"})
@MultipartConfig(maxFileSize = 100 * 1024 * 1024, maxRequestSize = 100 * 1024 * 1024)
public class LibraryControlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookName = request.getParameter(BOOK_NAME_PARAMETER);

        if (bookName != null) {
            FileUtils.writeFileToOutputStream(new FileUtils.IFileReadListener() {
                @Override
                public InputStream getInputStream() throws FileNotFoundException {
                    return new FileInputStream(getServletContext().getRealPath("/") + BOOK_STORAGE_PATH + bookName);
                }

                @Override
                public OutputStream getOutputStream() throws IOException {
                    return response.getOutputStream();
                }

                @Override
                public void fileFound() {
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment; filename=\"" + bookName + "\"");
                    response.setStatus(HttpServletResponse.SC_OK);
                }

                @Override
                public void fileNotFound() throws IOException {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Книга не найдена!");
                }
            });

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неправильный запрос (параметр book)!");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part filePart = request.getPart("book");
        if (filePart != null) {
            String fileName = filePart.getSubmittedFileName();
            if (fileName != null && !fileName.isEmpty()) {
                for (Part part : request.getParts()) {
                    part.write(getServletContext().getRealPath(BOOK_STORAGE_PATH) + fileName);
                }
                response.getWriter().println("Файл успешно загружен!");
            }

        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неправильный запрос");
        }
        response.sendRedirect(request.getContextPath() + "/library");
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookName = request.getParameter(BOOK_NAME_PARAMETER);
        if (bookName != null && !bookName.isEmpty()) {
            File file = new File(getServletContext().getRealPath("/") + BOOK_STORAGE_PATH + bookName);

            if (file.exists() && file.delete()) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("Книга удалена");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("Файл не найден или недоступен");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Неправильный запрос");
        }
    }
}


