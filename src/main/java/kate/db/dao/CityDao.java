package kate.db.dao;

import kate.db.model.City;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

public class CityDao {      // dao - data access object
    private final Connection connection;

    public CityDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<City> findById(long id) {
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from city where id = " + id);

            if (!resultSet.next()) {
                return Optional.empty();
            }

            String name = resultSet.getString("name");
            long population = resultSet.getLong("population");

            return Optional.of(new City(id, name, population));
        } catch (Exception e) {
            throw new RuntimeException(e);//origin exc never to be lost
        }

    }


}

// 1) add getAll() method -> return all cities, which type is it?
// 2) add 'business' method to that dao object
// 3) duplicates -> to optimize code ?
// 4) new class Country alike City and CountryDao alike CityDao -> the same methods to be added + new ones (e.g. the largest countries)

// + test