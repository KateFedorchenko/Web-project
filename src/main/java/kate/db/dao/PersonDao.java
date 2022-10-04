package kate.db.dao;

import kate.db.model.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Stack;

public class PersonDao {
    private Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Person> findByFirstAndLastName(String firstName, String lastName) {
        try {
//            Statement statement = connection.createStatement(); // 99% useless -> Usually we have params
//            ResultSet resultSet = statement.executeQuery("select * from persons where first_name = '" + firstName
//                    + "' and last_name = '" + lastName + "'");

            PreparedStatement preparedStatement = connection.prepareStatement("select * from persons where " +
                    "first_name = ? and last_name = ?");
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(makePersonFromRS(resultSet));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Person makePersonFromRS(ResultSet rs) throws SQLException {
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        BigDecimal weight = rs.getBigDecimal("weight");
        BigDecimal height = rs.getBigDecimal("height");

        return new Person(firstName, lastName, birthday, weight, height);
    }


}


// sql injections - youtube - computerphile channel. To protect from sql injections we use prepared statements
// Dao Code above to be remade due to new data from sql injections ?
