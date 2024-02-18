import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    //private -> ще се вижда само в нашия Main
    //static -> за да могат да я използват другите методи
    //final -> че няма да се промени

    private static final String CONNECTION_STRING =
            "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME =
            "minions_db";

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
                .getConnection(CONNECTION_STRING + DATABASE_NAME, props);

        String query = "SELECT name FROM minions";
        PreparedStatement statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %n", resultSet.getString("name"));
        }
    }
}
