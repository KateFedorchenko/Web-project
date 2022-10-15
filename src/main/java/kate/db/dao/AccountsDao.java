package kate.db.dao;

import kate.db.model.Account;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AccountsDao {
    private final Connection connection;

    public AccountsDao(Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    public Optional<Account> findById(int id){
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                        SELECT * FROM accounts 
                        WHERE id = ?
                       """
        );
        preparedStatement.setInt(1,id);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()){
            return Optional.empty();
        }
        return Optional.of(new Account(id,resultSet.getBigDecimal("sum")));
    }

    private void updateAccountBalance0(int id, BigDecimal change) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                """
                         UPDATE accounts
                         SET
                         sum = ?
                         WHERE id = ?
                        """
        );
        preparedStatement.setBigDecimal(1, change);
        preparedStatement.setInt(2, id);

        preparedStatement.executeUpdate();

    }

    public void updateAccountBalance(int id, BigDecimal change) {
        try {
            updateAccountBalance0(id, change);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
