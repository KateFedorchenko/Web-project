package kate.warehouse.dao;

import kate.db.model.City;
import kate.warehouse.model.Item;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private final Connection connection;

    public ItemDao(Connection connection) {
        this.connection = connection;
    }


    public List<Item> getAllItemsFromDB() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from item");

            List<Item> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(makeItemFromRS(resultSet));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Item makeItemFromRS(ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        BigDecimal quantity = rs.getBigDecimal("quantity");

        return new Item(id, name, quantity);
    }
}
