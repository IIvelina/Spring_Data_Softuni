import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task5ChangeTownNamesCasing {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {

        reader = new BufferedReader(new InputStreamReader(System.in));

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");


        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

        String country = reader.readLine();

       int rows = changeTownToUpperByCountry(country);

       if (rows == 0){
           System.out.println("No town names were affected.");
       }else {
           System.out.printf("%d town names were affected.%n", rows);

           townsToUpperByCountry(country);
       }
    }

    private static void townsToUpperByCountry(String country) throws SQLException {

        query = "SELECT name FROM towns WHERE country = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, country);
        ResultSet resultSet = statement.executeQuery();

        StringBuilder townNames = new StringBuilder();
        while (resultSet.next()) {
            townNames.append(resultSet.getString("name").toUpperCase()).append(", ");
        }

        if (townNames.length() > 0) {
            townNames.setLength(townNames.length() - 2);
            System.out.printf("[%s]%n", townNames.toString());
        }
    }

    private static int changeTownToUpperByCountry(String country) throws SQLException {

        query = "UPDATE towns\n" +
                "SET name = UPPER(name)\n" +
                "WHERE country = ?";

        statement = connection.prepareStatement(query);
        statement.setString(1, country);
        int rowsAffected = statement.executeUpdate();
        return rowsAffected;
    }
}
