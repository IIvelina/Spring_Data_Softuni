import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class task2AccessingDatabaseViaSimpleJavaApplication {
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
        props.setProperty("username", username);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/?user=root", props);

        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT CONCAT(first_name,\" \",last_name) AS full_name, salary \n" +
                        "FROM soft_uni.employees\n" +
                        "WHERE salary >= ?");


        System.out.print("Enter your salary:");
        String salary = scanner.nextLine();
        preparedStatement.setDouble(1, Double.parseDouble(salary));
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            System.out.println(resultSet.getString("full_name") + " " +  resultSet.getString("salary"));
        }
        connection.close();
    }
}
