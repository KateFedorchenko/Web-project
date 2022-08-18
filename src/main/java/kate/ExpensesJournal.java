package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExpensesJournal extends HttpServlet {
    /**
     * The expenses journal should save all numbers and sum them as a total amount of expenses.
     * The expenses journal should show the journal history.
     * Possible endpoints:  /expenses-journal?operation=addRecord&date=2022-08-18&amount=120
     *                      /expenses-journal?operation=showHistory
     *
     * @param req  date, number
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = req.getParameter("date");
        LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
        int amount = Integer.parseInt(req.getParameter("amount"));
        String operation = req.getParameter("operation");

        Map<LocalDate,Integer> localDateIntegerMap = new HashMap<>();
//        List<LocalDate> localDateList = new ArrayList<>();
        long sum = 0;
        if ("addRecord".equals(operation)) {
            localDateIntegerMap.put(localDate,amount);
            sum += amount;
            String htmlResponse = "<html>";
            htmlResponse += "<h3>The total expenses sum for now is " + sum + "</h3>";
            htmlResponse += "<p>The amount is " + amount + "</p>";
            htmlResponse += "<p>The date is " + localDate + "</p>";
            htmlResponse += "</html>";
            resp.getWriter().println(htmlResponse);
        }
        if ("showHistory".equals(operation)) {
            String htmlResponse = "<html>";
            htmlResponse += "<!DOCTYPE html>";
            htmlResponse += "<html><head>";
            htmlResponse += "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>";
            htmlResponse += "<title>Expenses journal</title></head>";
            htmlResponse += "<body>";
            htmlResponse += "<h1>Expenses journal history</h1>";
            htmlResponse += "<table><tr>\n" +
                    "<th>Amount</th>\n" +
                    "<th>Date</th>\n" +
                    "</tr>";
            Iterator<LocalDate> itr = localDateIntegerMap.keySet().iterator();
            while (itr.hasNext()) {
                htmlResponse += "<tr>";
                htmlResponse += "<td>" + itr.next() + "</td>";
                htmlResponse += "</tr>";
            }
            htmlResponse += "</body>";
            htmlResponse += "</html>";
            resp.getWriter().println(htmlResponse);
        }
    }
}
