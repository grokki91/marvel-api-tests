package config;

import utils.ReadProperties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DataBaseSetting {
    private static final String URL = ReadProperties.get("database_url");
    private static final String USER = ReadProperties.get("database_user");
    private static final String PASSWORD = ReadProperties.get("database_password");
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null ||  connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }

    public static void prepareTestData() throws SQLException {
        prepareUserData();
        prepareCharacterData();
    }

    private static void prepareUserData() {
        String date = createDate();
        try(Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO users (username, email, gender, birthday, created) values('Test', 'test@test.com', 'male', '2000-01-01', '" + date + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void prepareCharacterData() throws SQLException {
        String date = createDate();
        try(Statement stmt = getConnection().createStatement()) {
            stmt.executeUpdate("INSERT INTO character (alias, fullname, alignment, abilities, age, team, created) values('Wolverine', 'James Howlett', 'good', 'regeneration', '137', 'X-Men', '" + date + "')");
        }
    }

    public static void  cleanUp() throws SQLException {
        try(Statement stmt = getConnection().createStatement()) {
            stmt.execute("TRUNCATE TABLE users RESTART IDENTITY");
            stmt.execute("TRUNCATE TABLE character RESTART IDENTITY");
        } finally {
            closeConnection();
        }
    }

    private static String createDate() {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDateTime.now().format(pattern);
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
