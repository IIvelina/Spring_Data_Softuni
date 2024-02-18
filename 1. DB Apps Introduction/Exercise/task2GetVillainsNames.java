import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class task2GetVillainsNames {
    //private -> ще се вижда само в нашия Main
    //static -> за да могат да я използват другите методи
    //final -> че няма да се промени

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

        //2.	Get Villains’ Names
        getVillainsNamesAndCountOfMinions();

    }


    private static void getVillainsNamesAndCountOfMinions() throws SQLException {
        query = "SELECT v.name AS villain_name, COUNT(mv.minion_id) AS count\n" +
                "FROM villains v\n" +
                "JOIN minions_villains mv ON v.id = mv.villain_id\n" +
                "GROUP BY v.name\n" +
                "HAVING count > 15\n" +
                "ORDER BY count DESC";

        statement = connection.prepareStatement(query);

        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()){
            System.out.printf("%s %d%n", resultSet.getString(1),
                    resultSet.getInt(2));
        }
    }
}
