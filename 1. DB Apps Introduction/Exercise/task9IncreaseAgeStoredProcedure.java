import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;

public class task9IncreaseAgeStoredProcedure {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/";
    private static final String DATABASE_NAME = "minions_db";
    private static Connection connection;
    private static String query;
    private static BufferedReader reader;

    public static void main(String[] args) throws SQLException, IOException {


        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "12345");


        connection = DriverManager
                .getConnection(CONNECTION_STRING + DATABASE_NAME, properties);

        increaseAgeStoredProcedure();
    }

    private static void increaseAgeStoredProcedure() throws IOException, SQLException {
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter minion id:");
        int minionId = Integer.parseInt(reader.readLine());

        query = "CALL usp_get_older(?)";
        CallableStatement callableStatement =
                connection.prepareCall(query);

        callableStatement.setInt(1, minionId);
        callableStatement.execute();
    }
}

/*
DELIMITER $$
CREATE PROCEDURE usp_get_older(minion_id INT)
BEGIN
	UPDATE minions
    SET age = age + 1
    WHERE id = minion_id;
END $$
 */

