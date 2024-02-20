import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task8IncreaseMinionsAge {
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


        String[] input = reader.readLine().split(" ");

        for (int i = 0; i < input.length; i++) {
            int currentId = Integer.parseInt(input[i]);
            changeAge(currentId);
            changeName(currentId);
        }

        printAllMinions();

    }

    private static void printAllMinions() throws SQLException {
        query = "SELECT name, age FROM minions";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d%n",
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
        }

    }

    private static void changeName(int currentId) throws SQLException {
        query = "UPDATE minions\n" +
                "SET name = LOWER(name)\n" +
                "WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, currentId);
        statement.executeUpdate();
    }

    private static void changeAge(int currentId) throws SQLException {

        query = "UPDATE minions\n" +
                "SET age = age + 1\n" +
                "WHERE id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, currentId);
        statement.executeUpdate();
    }
}
