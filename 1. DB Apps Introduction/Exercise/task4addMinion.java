import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task4addMinion {
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
        properties.setProperty("password", "iV951222&");


        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

        String[] arr1 = reader.readLine().split(" ");
        String minionName = arr1[1];
        int age = Integer.parseInt(arr1[2]);
        String town = arr1[3];

        String[] arr2 = reader.readLine().split(" ");
        String villainName = arr2[1];
        System.out.println();

        int townId = findTownIdByName(town);
        if (townId == 0){
            //create town
            townId = createTown(town);
            System.out.printf("Town %s was added to the database.%n", town);
        }

        int minionId = createMinion(minionName, age, townId);
        int villainId = findVillainByName(villainName);
        if (villainId == 0){
            villainId = createVillain(villainName);
            System.out.printf("Villain %s was added to the database.%n", villainName);
        }

        populateMinionsVillains(minionId, villainId);
        System.out.printf("Successfully added %s to be minion of %s", minionName, villainName);
    }

    private static void populateMinionsVillains(int minionId, int villainId) throws SQLException {
        query = "INSERT INTO minions_villains VALUES (?, ?)";
        statement = connection.prepareStatement(query);
        statement.setInt(1, minionId);
        statement.setInt(2, villainId);
        statement.executeUpdate();
    }


    private static int createVillain(String villainName) throws SQLException {
        query = "INSERT INTO villains(name, evilness_factor) VALUE(?, 'evil')";
        statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        statement.executeUpdate();

        query = "SELECT id FROM villains WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }


    private static int findVillainByName(String villainName) throws SQLException {
        query = "SELECT id FROM villains WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, villainName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()){
            return resultSet.getInt("id");
        }
        return 0;
    }

    private static int findTownIdByName(String town) throws SQLException {
        query = "SELECT id FROM towns\n" +
                "WHERE name = ?";

        statement = connection.prepareStatement(query);
        statement.setString(1, town);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()){
            return  resultSet.getInt("id");
        }
        return 0;
    }

    private static int createTown(String town) throws SQLException {
        query = "INSERT INTO towns(name)\n" +
                "VALUE (?)";

        statement = connection.prepareStatement(query);
        statement.setString(1, town);
        statement.executeUpdate();

        query = "SELECT  id FROM  towns WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, town);
        ResultSet resultSet = statement.executeQuery();

        resultSet.next();
        return resultSet.getInt("id");
    }

    private static int createMinion(String minionName, int age, int townId) throws SQLException {

        query = "INSERT INTO minions(name, age, town_id)\n" +
                "VALUE\n" +
                "(?, ?, ?)";

        statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        statement.setInt(2, age);
        statement.setInt(3, townId);
        statement.executeUpdate();

        query = "SELECT  id FROM  minions WHERE name = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, minionName);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();

        return resultSet.getInt("id");
    }
}
