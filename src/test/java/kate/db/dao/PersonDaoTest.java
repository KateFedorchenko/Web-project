package kate.db.dao;

import kate.db.model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    Person EXISTING_PERSON = new Person("Bob", "Bobby", LocalDate.parse("1995-10-02"),
            new BigDecimal(50), new BigDecimal(180));

    Person NON_EXISTING_PERSON = new Person("Ivan", "Bof", LocalDate.parse("1495-10-02"),
            new BigDecimal(40), new BigDecimal(170));

    Person UPDATED_EXISTING_PERSON = new Person("Bob", "Bobby", LocalDate.parse("1995-01-02"),
            new BigDecimal(30), new BigDecimal(150));


    PersonDao personDao;
    Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "1234");
        personDao = new PersonDao(connection);

        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE persons");        // better than to do it in @AfterEach !!

        PreparedStatement preparedStatement = connection.prepareStatement("insert into persons (" +
                "first_name, last_name, birthday, weight, height) values(?,?,?,?,?)");
        preparedStatement.setString(1, EXISTING_PERSON.getFirstName());
        preparedStatement.setString(2, EXISTING_PERSON.getLastName());
        preparedStatement.setDate(3, Date.valueOf(EXISTING_PERSON.getBirthday()));
        preparedStatement.setBigDecimal(4, EXISTING_PERSON.getWeight());
        preparedStatement.setBigDecimal(5, EXISTING_PERSON.getHeight());

        preparedStatement.executeUpdate();
    }

//    @AfterEach
//    void cleanUp() throws Exception {
//        Statement statement = connection.createStatement();
//        statement.execute("TRUNCATE TABLE persons");
//    }

    @Test
    void whenRequestedExistingPersonShouldReturnNotEmptyResult() {
        Optional<Person> actual = personDao.findByFirstAndLastName(EXISTING_PERSON.getFirstName(), EXISTING_PERSON.getLastName());
        assertTrue(actual.isPresent());
        assertEquals(EXISTING_PERSON, actual.get());
    }

    @Test
    void whenCreatedNewPersonShouldFindCorrespondingRecord() {
        personDao.createPerson(NON_EXISTING_PERSON);
        Optional<Person> actual = personDao.findByFirstAndLastName(NON_EXISTING_PERSON.getFirstName(), NON_EXISTING_PERSON.getLastName());
        assertEquals(NON_EXISTING_PERSON, actual.get());
    }

    @Test
    void whenUpdatedExistingPersonShouldFindUpdatedRecord() {
        personDao.updatePerson(UPDATED_EXISTING_PERSON);
        Optional<Person> optionalPerson = personDao.findByFirstAndLastName(EXISTING_PERSON.getFirstName(), EXISTING_PERSON.getLastName());
        assertEquals(UPDATED_EXISTING_PERSON,optionalPerson.get());
    }

    @Test
    void whenUpdatedNonExistingPersonShouldNotFindUpdatedRecord() {
        Exception exception = assertThrows(Exception.class, () -> personDao.updatePerson(NON_EXISTING_PERSON));
        assertEquals("No such person found!", exception.getMessage());
    }

    @Test
    void whenDeletedExistingPersonShouldNotFindRecord(){
        personDao.deleteByPrimaryKeys(EXISTING_PERSON.getFirstName(),EXISTING_PERSON.getLastName());
        Optional<Person> optionalPerson = personDao.findByFirstAndLastName(EXISTING_PERSON.getFirstName(), EXISTING_PERSON.getLastName());
        assertTrue(optionalPerson.isEmpty());
    }

    @Test
    void whenDeletedAllRowsShouldReturnNothing(){
        personDao.deleteAll();
        Optional<Person> optionalPerson = personDao.findByFirstAndLastName(EXISTING_PERSON.getFirstName(), EXISTING_PERSON.getLastName());
        assertTrue(optionalPerson.isEmpty());
    }


}

// DeleteByPrimaryKeys(), DeleteAll() tests!!!!!!!!!!!
// Update in case there is no such record in db !!!!