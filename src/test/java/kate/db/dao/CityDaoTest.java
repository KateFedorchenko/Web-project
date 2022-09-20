package kate.db.dao;

import kate.db.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CityDaoTest {
    CityDao cityDao;

    @BeforeEach
    void setUp() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        cityDao = new CityDao(DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "1234"));
    }

    @Test
    void whenRequestCityWithId1ShouldReturnKabul(){
        Optional<City> actual = cityDao.findById(1);
        City expected = new City(1,"Kabul",1_780_000);
        assertEquals(expected,actual.get());
    }

    @Test
    void whenRequestCityWithId0ShouldReturnNull(){
        Optional<City> actual = cityDao.findById(0);
        assertTrue(actual.isEmpty());
    }



}