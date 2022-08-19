package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = "/servlet", name = "Servlet")
public class Servlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getReader().lines()
                .forEach(System.out::println);
        System.out.println("req.getContentLength() = " + req.getContentLength());
        resp.getWriter().println("\nPOST was called");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       resp.getWriter().println("\nGet was called");
    }
}

// 1) create DataBase servlet @WebServlet
// GET: param key -> returns value (eg. /database?key=foo), otherwise HTTP code
// POST: add or update the value for the key (eg. /database?key=foo)
// value to be added in body

// 2) adapt the code with concurrent issues