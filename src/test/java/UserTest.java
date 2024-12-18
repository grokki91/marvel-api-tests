import api.Request;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.ErrorMessages;

@Owner("QA")
public class UserTest {
    @Test
    @Description("Send request without bearer token")
    public void checkJwtMissing() {
        Response res = Request.get("/api/characters");
        String actual = res.jsonPath().get("Message");

        Assertions.assertEquals(ErrorMessages.JWT_MISSING.getMessage(), actual);
        Assertions.assertEquals(401, res.statusCode());
    }

    @Test
    public void getUsers() {

    }
}
