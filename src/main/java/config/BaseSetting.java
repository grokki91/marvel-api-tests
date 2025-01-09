package config;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import java.sql.SQLException;

public class BaseSetting {

    @BeforeAll
    public static void setup() throws SQLException {
        DataBaseSetting.prepareTestData();
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        DataBaseSetting.cleanUp();
    }
}
