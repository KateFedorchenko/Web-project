package kate.db.dao;

import kate.db.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

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
        City expected = new City(1,"Kabul",1_780_000,"AFG");
        assertEquals(expected,actual.get());// why null?
    }

    @Test
    void whenRequestCityWithId0ShouldReturnNull(){
        Optional<City> actual = cityDao.findById(0);
        assertTrue(actual.isEmpty());
    }

//    @Test
//    void whenRequestedAllCitiesShouldReturnList(){
//        List<String> actual = cityDao.getAll();
//        List<String> expected = new ArrayList<>(List.of("Kabul","Qandahar"));   // how to check such things? when I do not know the limit.
//        assertEquals(expected,actual);
//    }
//
//    @Test
//    void whenRequested10MostPopulatedCitiesShouldReturnList(){
//        List<String> actual = cityDao.find10MostPopulatedCities();
//        List<String> expected = new ArrayList<>(List.of("Kabul","Qandahar"));
//        assertEquals(expected,actual);
//    }



}