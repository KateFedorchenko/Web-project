package kate.db.dao;

import kate.db.model.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class PersonDao {
    private Connection connection;

    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<Person> findByFirstAndLastName(String firstName, String lastName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from persons where " +
                    "first_name = ? and last_name = ?");
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.next()) {
                return Optional.empty();
            }

            return Optional.of(makePersonFromRS(resultSet));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createPerson(Person person) {
        try {
            createPerson0(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createPerson0(Person person) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("insert into persons (" +
                "first_name, last_name, birthday, weight, height) values(?,?,?,?,?)");
        preparedStatement.setString(1, person.getFirstName());
        preparedStatement.setString(2, person.getLastName());
        preparedStatement.setDate(3, Date.valueOf(person.getBirthday()));
        preparedStatement.setBigDecimal(4, person.getWeight());
        preparedStatement.setBigDecimal(5, person.getHeight());

        preparedStatement.executeUpdate();
    }

    public void updatePerson(Person person) {
        try {
            updatePerson0(person);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updatePerson0(Person person) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                        update persons
                        set
                        birthday = ?,
                        weight = ?,
                        height = ?
                        where first_name = ? and last_name = ?"""
        );

        preparedStatement.setDate(1, Date.valueOf(person.getBirthday()));
        preparedStatement.setBigDecimal(2, person.getWeight());
        preparedStatement.setBigDecimal(3, person.getHeight());
        preparedStatement.setString(4, person.getFirstName());
        preparedStatement.setString(5, person.getLastName());

        preparedStatement.executeUpdate();
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


// DeleteByPrimaryKeys(){}
// DeleteAll(){} means delete all records in db --- NO TRUNCATE here!!!
