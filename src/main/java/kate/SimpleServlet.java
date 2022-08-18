package kate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SimpleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int number1 = Integer.parseInt(req.getParameter("number1"));
        int number2 = Integer.parseInt(req.getParameter("number2"));
        // operation in (sum,minus,divide,multiply)
        String operation = req.getParameter("operation");
        int result = 0;                     //TODO how not to initialize result variable here
        if ("minus".equals(operation)) {
            result = number1 - number2;
        }
        if ("sum".equals(operation)) {
            result = number1 + number2;
        }
        if ("multiply".equals(operation)) {
            result = number1 * number2;
        }
        if ("divide".equals(operation)) {
            result = number1 / number2;
        }
        resp.getWriter().println(result);
    }

}
// /simple-servlet?number1=1&number2=4&operation=divide



// servlet with any state - 2 params = 1 - date, 2 - number
// it should sum
// /expenses-journal?op=addRecord&date=...&sum=...
// /expenses-journal?op=showHistory


// + html decorations maybe

//doGet()
// set JETTY_HOME=C:\Users\ekate\Desktop\jetty-home-11.0.11\jetty-home-11.0.11
// set JAVA_HOME=D:\jdk\jdk-16.0.2\bin
//C:\Users\ekate\Desktop\jetty-home-11.0.11\jetty-base>%JAVA_HOME%\java -jar %JETTY_HOME%\start.jar

//idea update
// postman download