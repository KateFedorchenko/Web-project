package kate.db.dao;

import kate.db.model.City;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

            return Optional.of(makeCityFromRS(resultSet));
        } catch (Exception e) {
            throw new RuntimeException(e);//origin exc never to be lost
        }

    }

    public List<City> getAll() {
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select id, name, population from city");

            List<City> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(makeCityFromRS(resultSet));
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Business Method
     *
     * @return 10 Most Populated Cities
     */
    public List<City> find10MostPopulatedCities() {
        try {
            return find10MostPopulatedCities0();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<City> find10MostPopulatedCities0() throws SQLException{
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("select id, name, population from city order by population desc limit 10");

        List<City> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(makeCityFromRS(resultSet));
        }
        return list;
    }

    private City makeCityFromRS(ResultSet rs) throws SQLException{

        long id = rs.getLong("id");
        String name = rs.getString("name");
        long population = rs.getLong("population");
        String countryCode = rs.getString("countryCode");

        return new City(id,name,population,countryCode);

    }


}

// 1) add getAll() method -> return all cities, which type is it?
// 2) add 'business' method to that dao object
// 3) duplicates -> to optimize code ?
// 4) new class Country alike City and CountryDao alike CityDao -> the same methods to be added + new ones (e.g. the largest countries)

// + test