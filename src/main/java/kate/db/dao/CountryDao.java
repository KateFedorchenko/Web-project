package kate.db.dao;

import kate.db.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryDao {
    private final Connection connection;

    public CountryDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Country> findByCode(String code) {
        try {
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery("select * from country where code=\"" + code + "\"");

            PreparedStatement preparedStatement = connection.prepareStatement("select * from country where code = ?");
            preparedStatement.setString(1, code);

            ResultSet resultSet = preparedStatement.executeQuery();


            if (!resultSet.next()) {
                return Optional.empty();
            }

            String name = resultSet.getString("name");
            String continent = resultSet.getString("continent");
            long population = resultSet.getLong("population");
            String localName = resultSet.getString("localName");
            String indepYear = resultSet.getString("indepYear");

            return Optional.of(new Country(code, name, continent, population, localName, indepYear));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAll() {
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select name from country");

            return createListAndAddDataFromDB(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Business Method
     *
     * @return 10 Most Populated Cities
     */
    public List<String> find10MostPopulatedCountries() {
        try {
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select name from country order by population desc limit 10");

            PreparedStatement preparedStatement = connection.prepareStatement("select name from country order by population desc limit 10");

            return createListAndAddDataFromDB(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> findAllCountriesWhichStartsAt(String c) {
        try {
//            Statement statement = connection.createStatement();
//
//            ResultSet resultSet = statement.executeQuery("select * from country where name like \"" + c + "%\"");
//
            PreparedStatement preparedStatement = connection.prepareStatement("select * from country where name like ?");
            preparedStatement.setString(1, c + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            return createListAndAddDataFromDB(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Attempt to get rid of duplicates.
     *
     * @param resultSet
     * @return list
     */
    public List<String> createListAndAddDataFromDB(ResultSet resultSet) {
        try {
            List<String> list = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                list.add(name);
            }
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
