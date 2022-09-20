package kate.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");      //load class
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/world","root","1234");
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from country");
        while (resultSet.next()){
            String name = resultSet.getString("name");
            System.out.println("name = " + name);
        }


    }
}

// dbeaver download
