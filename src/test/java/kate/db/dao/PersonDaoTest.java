package kate.db.dao;

import kate.db.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    Person EXISTING_PERSON = new Person("Bob", "Bobby", LocalDate.parse("1995-10-02"),
            new BigDecimal(50), new BigDecimal(180));

    Person NON_EXISTING_PERSON = new Person("Ivan", "Bof", LocalDate.parse("1495-10-02"),
            new BigDecimal(40), new BigDecimal(170));

    PersonDao personDao;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        personDao = new PersonDao(DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "1234"));
    }

    @Test
    void whenRequestedExistingPersonShouldReturnNotEmptyResult() {
        Optional<Person> actual = personDao.findByFirstAndLastName(EXISTING_PERSON.getFirstName(), EXISTING_PERSON.getLastName());
        assertTrue(actual.isPresent());
        assertEquals(EXISTING_PERSON,actual.get());
    }

}