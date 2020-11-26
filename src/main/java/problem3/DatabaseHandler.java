package problem3;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.Set;

public class DatabaseHandler {
    private static DatabaseHandler dbHandler = new DatabaseHandler();

    private DatabaseHandler() {}

    public static DatabaseHandler getInstance() {
        return dbHandler;
    }

    private Properties loadConfig(String configPath) throws IOException {

        // Specify which keys must be in properties file
        Set<String> required = new HashSet<>();
        required.add("username");
        required.add("password");
        required.add("database");
        required.add("hostname");

        // Load properties file
        Properties config = new Properties();
        config.load(new FileReader(configPath));

        // Check that required keys are present
        if (!config.keySet().containsAll(required)) {
            String error = "Must provide the following in properties file: ";
            throw new InvalidPropertiesFormatException(error + required);
        }

        return config;
    }

    public void storeNameInDatabase(String name) throws IOException {
        Properties config = loadConfig("database.properties");

        // Create database URI in proper format
        String uri = String.format("jdbc:mysql://%s/%s", config.getProperty("hostname"),
                config.getProperty("database"));
        uri = uri + "?serverTimezone=UTC";
        System.out.println("uri = " + uri);
        System.out.println(config.getProperty("username"));

        // Create database login properties
        Properties login = new Properties();
        login.put("user", config.getProperty("username"));
        login.put("password", config.getProperty("password"));

        PreparedStatement sql; // prepared statement
        try (Connection dbConnection = DriverManager.getConnection(uri, login)) {
            sql = dbConnection.prepareStatement("INSERT INTO names (name) VALUES (?)");
            sql.setString(1, name);
            sql.executeUpdate();
        }
        catch(SQLException e) {
            System.out.println(e);
        }
    }
}
