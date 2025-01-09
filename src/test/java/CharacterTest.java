import api.Request;
import config.BaseSetting;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.ErrorMessages;

@Owner("QA")
public class CharacterTest extends BaseSetting {
    private final String endpoint = "/api/characters";

    @Test
    @Description("Send request without jwt in header")
    public void checkJwtMissing() {
        Response res = Request.getWithoutAuth(endpoint);
        String actual = res.jsonPath().get("Message");

        Assertions.assertEquals(ErrorMessages.JWT_MISSING.getMessage(), actual);
        Assertions.assertEquals(401, res.statusCode());
    }

    @Test
    @Description("Get list of characters")
    public void checkListCharacters() {
        Response res = Request.get(endpoint);
        System.out.println(res.asString());
        Assertions.assertEquals(200, res.statusCode());
    }

    @Test
    @Description("Get character by ID")
    public void checkCurrentCharacter() {
        Response res = Request.get(endpoint + "/1");
        String alias = res.jsonPath().getString("alias");

        Assertions.assertEquals("Wolverine", alias);
        Assertions.assertEquals(200, res.statusCode());
    }
}
