import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.sql.*;
        import java.util.Properties;

public class task3_1GetMinionNames {
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


        getMinionNameExercise();
    }

    private static void getMinionNameExercise() throws IOException, SQLException {
        System.out.print("Enter Villain id:");
        int villain_id = Integer.parseInt(reader.readLine());

        if(!checkIfEntityExists(villain_id, "villains")){
            System.out.printf("No villain with ID %d exists in the database.", villain_id);
            return;
        }

        System.out.printf("Villain: %s%n", getEntityNameById(villain_id, "villains"));

        getMinionsAndAgeByVillainId(villain_id);
    }

    private static void getMinionsAndAgeByVillainId(int villain_id) throws SQLException {
        query = "SELECT  m.name, m.age FROM minions AS m\n" +
                "JOIN minions_villains mv on m.id = mv.minion_id\n" +
                "WHERE mv.villain_id = ?";

        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);

        ResultSet resultSet = statement.executeQuery();
        int count = 1;
        while (resultSet.next()){
            System.out.printf("%d. %s %d%n",
                    count,
                    resultSet.getString("name"),
                    resultSet.getInt("age"));
            count++;
        }
    }

    private static String getEntityNameById(int entityId, String tableName) throws SQLException {
        query = "SELECT name FROM " + tableName + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, entityId);

        ResultSet resultSet = statement.executeQuery();

        return resultSet.next() ? resultSet.getString("name") : null;
    }


    private static boolean checkIfEntityExists(int villain_id, String villains) throws SQLException {
        query = "SELECT * FROM " + villains + " WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villain_id);

        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }
}
