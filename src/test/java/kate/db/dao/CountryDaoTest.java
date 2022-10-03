package kate.db.dao;

import kate.db.model.City;
import kate.db.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CountryDaoTest {
    CountryDao countryDao;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        countryDao = new CountryDao(DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "1234"));
    }

    @Test
    void whenRequestCountryWithCodeABWShouldReturnAruba() {
        Optional<Country> actual = countryDao.findByCode("ABW");
        Country expected = new Country("ABW", "Aruba", "North America", 103000, "Aruba", null);
        assertEquals(expected, actual.get());
    }

    @Test
    void whenRequestCountryWithCodeAAAAShouldReturnNull() {
        Optional<Country> actual = countryDao.findByCode("AAAA");
        assertTrue(actual.isEmpty());
    }

    @Test
    void whenRequestCountryWhichStartsAtKShouldReturnList() {
        List<String> actual = countryDao.findAllCountriesWhichStartsAt("K");
        List<String> expected = new ArrayList<>(List.of("Kazakstan","Kenya","Kyrgyzstan","Kiribati","Kuwait"));
        assertEquals(expected,actual);
    }


}