package kate.warehouse;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kate.db.dao.AccountsDao;
import kate.warehouse.dao.ItemDao;
import kate.warehouse.model.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class Warehouse extends HttpServlet {
    Connection connection;
    ItemDao itemDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if(("showItems").equals(operation)){
            resp.getWriter().println(getItemList());
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if(("addRecord").equals(operation)){
            String name = req.getParameter("name");
            BigDecimal quantity = new BigDecimal(req.getParameter("quantity"));
            addRecord(name,quantity);
            resp.getWriter().println("The record has been added");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if(("removeRecord").equals(operation)){
            String name = req.getParameter("name");
            removeRecord(name);
            resp.getWriter().println("The record has been removed");
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

    public void addRecord(String name, BigDecimal quantity){
        try{
            addRecord0(name,quantity);
        } catch (Exception s){
            throw new RuntimeException(s);
        }
    }

    private void addRecord0(String name, BigDecimal quantity) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "1234");
        itemDao = new ItemDao(connection);
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                        insert into items (name, quantity) values (?,?);
                        """
        );
        preparedStatement.setString(1, name);
        preparedStatement.setBigDecimal(2, quantity);

        preparedStatement.executeUpdate();
    }

    public void removeRecord(String name){
        try {
            removeRecord0(name);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private void removeRecord0(String name) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehouse", "root", "1234");
        itemDao = new ItemDao(connection);
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                        Delete from items
                        where name = ?;
                        """
        );
        preparedStatement.setString(1,name);
        preparedStatement.execute();
    }


}
