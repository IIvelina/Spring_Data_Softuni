import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class task3GetMinionNames {
    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME =
            "minions_db";

    private static Connection connection;
    private static String query;
    private static PreparedStatement statement;

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username default (root):");
        String username = scanner.nextLine();

        username = username.equals("") ? "root" : username;

        System.out.println();

        System.out.print("Enter your password:");
        String password = scanner.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", username);
        props.setProperty("password", password);

        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_NAME, props);

        //3.	Get Minion Names
        getMinionsNamesAndAge();

    }

    private static void getMinionsNamesAndAge() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        query = "SELECT m.name, m.age, v.name AS vil_name\n" +
                "FROM minions AS m\n" +
                "JOIN minions_villains mv ON m.id = mv.minion_id\n" +
                "JOIN villains AS v ON mv.villain_id = v.id\n" +
                "WHERE mv.villain_id = ?";

        statement = connection.prepareStatement(query);

        int villain_id = Integer.parseInt(scanner.nextLine());
        statement.setInt(1, villain_id);

        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No minions found for villain with ID " + villain_id);
            return;
        }else {
            System.out.print("Villain: " + resultSet.getString("vil_name"));
            System.out.println();
            int count = 1;

            do {
                System.out.printf("%d. %s %d%n",
                        count,
                        resultSet.getString("name"),
                        resultSet.getInt("age"));
                count++;
            } while (resultSet.next());
        }
    }
}
