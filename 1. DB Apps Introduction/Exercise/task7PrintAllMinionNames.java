import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task7PrintAllMinionNames {
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

        int allMinions = findHowManyMinionsHaveInDb();

        printAllNames(allMinions);
    }

    private static void printAllNames(int allMinions) throws SQLException {

        query = "SELECT name FROM minions\n" +
                "ORDER BY id\n" +
                "LIMIT 1;";
        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        String nameMinionFirst = resultSet.getString("name");
        System.out.println(nameMinionFirst);

        query = "SELECT name FROM minions\n" +
                "ORDER BY id DESC\n" +
                "LIMIT 1";

        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        resultSet.next();
        String lastNameMinion = resultSet.getString("name");
        System.out.println(lastNameMinion);

        int countFirst = 1;
        int countLast = 1;
        int totalIterations = (allMinions + 1) / 2;
        for (int i = 0; i < totalIterations - 1; i++) {

            query = "SELECT name FROM minions ORDER BY id LIMIT 1 OFFSET ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, countFirst);
            resultSet = statement.executeQuery();
            resultSet.next();
            String nextName = resultSet.getString("name");
            System.out.println(nextName);
            countFirst++;

            query = "SELECT name FROM minions ORDER BY id DESC LIMIT 1 OFFSET ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, countLast);
            resultSet = statement.executeQuery();
            resultSet.next();
            String beforeLastName = resultSet.getString("name");
            System.out.println(beforeLastName);
            countLast++;
        }
    }

    private static int findHowManyMinionsHaveInDb() throws SQLException {

        query = "SELECT COUNT(*) AS total_minions\n" +
                "FROM minions";

        statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("total_minions");
        return count;
    }
}
