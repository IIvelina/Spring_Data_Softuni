import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task6RemoveVillain {
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


        int villainId = Integer.parseInt(reader.readLine());

        int affectedRows = checkIfVillainExist(villainId);

        if (affectedRows == 0){
            System.out.println("No such villain was found");
        }else {

            int rowsAff = removeVillainIdFromMinionVillainTable(villainId);

            String villainByIdForDelete = findVillainByIdForDelete(villainId);

            villainDeleted(villainId);

            System.out.printf("%s was deleted%n", villainByIdForDelete);
            System.out.printf("%d minions released", rowsAff);
        }
    }

    private static String findVillainByIdForDelete(int villainId) throws SQLException {
        query = "SELECT name FROM villains WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getString("name");
        }
        return null;
    }

    private static void villainDeleted(int villainId) throws SQLException {
        query = "DELETE FROM villains WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);
        statement.executeUpdate();
    }

    private static int removeVillainIdFromMinionVillainTable(int villainId) throws SQLException {

        query = "DELETE FROM minions_villains WHERE villain_id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        int rowsAffected = statement.executeUpdate();
        return rowsAffected;
    }


    private static int checkIfVillainExist(int villainId) throws SQLException {
        query = "SELECT COUNT(*) AS count FROM villains WHERE id = ?";
        statement = connection.prepareStatement(query);
        statement.setInt(1, villainId);

        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        int count = resultSet.getInt("count");
        return count;
    }
}
