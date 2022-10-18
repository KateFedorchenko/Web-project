package kate.warehouse;

import com.mysql.cj.xdevapi.JsonArray;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kate.warehouse.dao.ItemDao;
import kate.warehouse.model.Item;

import java.io.IOException;
import java.util.List;

public class Warehouse extends HttpServlet {
    ItemDao itemDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String operation = req.getParameter("operation");
        if(("showItems").equals(operation)){
//            resp.getWriter(getItemList());
        }

    }


//    private JsonArray getItemList(){
//        List<Item> allItemsFromDB = itemDao.getAllItemsFromDB();
////        return JSONArray.toJSONString(allItemsFromDB);
//    }
}
