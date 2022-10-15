package kate.db.dao;

import kate.db.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AccountsDaoTest {
    AccountsDao accountsDao;
    Connection connection;

    @BeforeEach
    void setUp() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "root", "1234");
        accountsDao = new AccountsDao(connection);

        Statement statement = connection.createStatement();
        statement.execute("TRUNCATE TABLE accounts");

        statement.execute(
                """
                        INSERT INTO accounts
                        VALUES(1,1000);             
                        """
        );
        statement.execute(
                """
                        INSERT INTO accounts
                        VALUES(2,2000);             
                        """
        );
        connection.setAutoCommit(false);
    }

    @Test
    void shouldTransferMoneyFromOneAccountToAnother() throws Exception{
//        BigDecimal sum = new BigDecimal(100);
        accountsDao.updateAccountBalance(1, new BigDecimal(900));
        System.out.println("******************");
        Thread.sleep(10_000);
        accountsDao.updateAccountBalance(2, new BigDecimal(2100));

        connection.commit();

        assertEquals(new BigDecimal(900),getSum(1));
        assertEquals(new BigDecimal(2100),getSum(2));
    }

    BigDecimal getSum(int id){
        return accountsDao.findById(id).get().getSum();
    }

}

// JS tricks --? on Wed
// SimpleJSON -- create several Servlet with the help of SimpleJSON --> return only JSON!  NO HTML!!!!
// (1 - to return the list of goods and the quantity (draw the table)), TO START WITH!!TODO
// (2 - to add new goods in DB)
// (3 - to save or discard the changes).

// separate project !