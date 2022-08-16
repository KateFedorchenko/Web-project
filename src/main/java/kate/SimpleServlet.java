package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SimpleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String sum = req.getParameter("+");
        String multiply = req.getParameter("*");
        String minus = req.getParameter("-");
        resp.getWriter().write(1 +sum + 2);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("Received numbers!");
    }
}

// write servlet with 3 params = 2 numbers and 1 string
// string = type of operation (possible values: + - / *)
// 2 - servlet with any state - 2 params = 1 - date, 2 - number
// it should sum
// /expenses-journal?op=addRecord&date=...&sum=...
// /expenses-journal?op=showHistory

// + html decorations maybe

//doGet()
// set JETTY_HOME=C:\Users\ekate\Desktop\jetty-home-11.0.11\jetty-home-11.0.11
// set JAVA_HOME=D:\jdk\jdk-16.0.2\bin
//C:\Users\ekate\Desktop\jetty-home-11.0.11\jetty-base>%JAVA_HOME%\java -jar %JETTY_HOME%\start.jar