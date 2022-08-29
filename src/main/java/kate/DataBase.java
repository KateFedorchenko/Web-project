package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet(urlPatterns = "/database", name = "DataBase")
public class DataBase extends HttpServlet {
    private Map<String, Integer> hm = new HashMap<>();

    /**
     * localhost:8080/database?operation=find&key=foo
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        String key = req.getParameter("key");
        if ("find".equals(operation)) {
            if (hm.containsKey(key)) {
                resp.getWriter().println("The value " + hm.get(key) + " stands for the key " + key);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().println("No such operation exists in the database!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * POST with body
     * localhost:8080/database?operation=addOrUpdate&key=foo
     * body: 1
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int value = Integer.parseInt(req.getReader().readLine());
        String operation = req.getParameter("operation");
        if ("addOrUpdate".equals(operation)) {
            String key = req.getParameter("key");
            if (hm.containsKey(key)) {
                resp.getWriter().println("The previous value is " + hm.get(key) + "." +
                        " The new data is " + value + "." +
                        " The inserted data has increased the value.");
            } else {
                resp.getWriter().println("The new value " + value + " and key " + key + " have been created!");
            }
            hm.compute(key, (k, oldValue) -> oldValue == null ? value : value + oldValue);
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}


// create DataBase servlet @WebServlet
// GET: param key -> returns value (eg. /database?find=foo), otherwise HTTP code
// POST: add or update the value for the key (eg. /database?key=foo&value=1)
// value to be added in body