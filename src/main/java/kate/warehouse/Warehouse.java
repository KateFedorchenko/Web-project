package kate.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kate.db.dao.AccountsDao;
import kate.warehouse.dao.ItemDao;
import kate.warehouse.model.Item;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Warehouse extends HttpServlet {
    Connection connection;
    ItemDao itemDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if(("showItems").equals(operation)){
            resp.getWriter().println(getItemList());
        }

    }


    private String getItemList0() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "1234");
        itemDao = new ItemDao(connection);
        List<Item> allItemsFromDB = itemDao.getAllItemsFromDB();
        JsonSerializerImpl jsonSerializer = new JsonSerializerImpl();

        return jsonSerializer.writeAsString(allItemsFromDB);
    }

    public String getItemList(){
        try{
            return getItemList0();
        }catch (Exception s){
            throw new RuntimeException(s);
        }
    }
}
