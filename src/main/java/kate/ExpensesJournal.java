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
    private Map<LocalDate, Integer> expensesByDate = new HashMap<>();

    /**
     * The expenses journal should save all numbers and sum them as a total amount of expenses.
     * The expenses journal should show the journal history.
     * Possible endpoints:  /expenses-journal?operation=addRecord&date=2022-08-18&amount=120
     * /expenses-journal?operation=showHistory
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
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");//may be excluded, because datetimeformatter is default
            String dateString = req.getParameter("date");
            LocalDate localDate = LocalDate.parse(dateString, dateTimeFormatter);
            int amount = Integer.parseInt(req.getParameter("amount"));
            htmlResponse.append(addRecord(localDate, amount));
        }

        if ("showHistory".equals(operation)) {
            htmlResponse.append(showHistory());
        }
        htmlResponse.append("</body></html>");
        resp.getWriter().println(htmlResponse);
    }

    private String addRecord(LocalDate date, int amount) {
        // Alternative to Map.compute
//        if(expensesByDate.containsKey(date)){
//            int newAmonut = expensesByDate.get(date) + amount;
//            expensesByDate.put(date,newAmonut);
//        } else {
//            expensesByDate.put(date,amount);
//        }
        expensesByDate.compute(date, (key, oldValue) -> oldValue == null ? amount : amount + oldValue);// threadsafe in case of concurrent HM

        sum += amount;

        return "<h3>The total expenses sum for now is " + sum + "</h3>" +
                "<p>The amount is " + amount + "</p>" +
                "<p>The date is " + date + "</p></html>";
    }

    private String showHistory() {
        StringBuilder htmlResponse = new StringBuilder();
        htmlResponse.append("<h2>Expenses Journal</h2><table border=\"1\"><tr><th>Amount</th><th>Date</th></tr>");
        for (Map.Entry<LocalDate, Integer> e : expensesByDate.entrySet()) {
            int expense = e.getValue();
            LocalDate date = e.getKey();

            htmlResponse.append("<tr>");
            htmlResponse.append("<td>").append(expense).append("</td>");
            htmlResponse.append("<td>").append(date).append("</td>");
            htmlResponse.append("</tr>");
        }
        htmlResponse.append("</table>");
        return htmlResponse.toString();
    }
}
