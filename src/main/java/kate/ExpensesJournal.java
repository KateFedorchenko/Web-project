package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ExpensesJournal extends HttpServlet {
    private long sum = 0;
    private List<LocalDate> localDateList = new ArrayList<>();
    private List<Integer> integerList = new ArrayList<>();

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
        String operation = req.getParameter("operation");
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<html><!DOCTYPE html><html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>")
                .append("<title>Expenses journal</title></head><body>");

        if ("addRecord".equals(operation)) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String dateString = req.getParameter("date");
            LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
            int amount = Integer.parseInt(req.getParameter("amount"));
            integerList.add(amount);
            localDateList.add(localDate);
            sum += amount;
            htmlResponse.append("<h3>The total expenses sum for now is " + sum + "</h3>")
                    .append("<p>The amount is " + amount + "</p>")
                    .append("<p>The date is " + localDate + "</p></html>");
            resp.getWriter().println(htmlResponse);
        }

        if ("showHistory".equals(operation)) {
            htmlResponse.append("<h2>Expenses Journal</h2><table border=\"1\"><tr><th>Amount</th><th>Date</th></tr>");
            for (Integer a : integerList) {
                htmlResponse.append("<tr><td>" + a + "</td></tr>");
            }
            for (LocalDate d : localDateList) {
                htmlResponse.append("<tr><td>" + d + "</td></tr>");
            }
            htmlResponse.append("</table>");
            resp.getWriter().println(htmlResponse);
        }
        htmlResponse.append("</body></html>");
    }
}
