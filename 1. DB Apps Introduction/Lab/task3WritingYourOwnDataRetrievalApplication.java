import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class task3WritingYourOwnDataRetrievalApplication {
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

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", props);


        String checkUser = "SELECT u.user_name, u.first_name, u.last_name, COUNT(ug.user_id) AS user_count\n" +
                "FROM users AS u\n" +
                "JOIN users_games AS ug ON u.id = ug.user_id\n" +
                "WHERE u.user_name = ?\n" +
                "GROUP BY u.user_name, u.id;\n";

        PreparedStatement preparedStatement = connection.prepareStatement(checkUser);

        String user_name = scanner.nextLine();

        preparedStatement.setString(1, user_name);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next()) {
            System.out.println("No such user exists");
        } else {
            String retrievedUsername = resultSet.getString("user_name");
            int gameCount = resultSet.getInt("user_count");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            System.out.println("Username: " + retrievedUsername);
            System.out.println( first_name + " " + last_name + " " + "has played " + gameCount + " games");
        }
        connection.close();
    }
}

