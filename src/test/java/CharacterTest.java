import api.Request;
import config.BaseSetting;
import data.RandomGenerateCharacter;
import dto.request.CharacterRequest;
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
        Response response = Request.getWithoutAuth(endpoint);
        String actual = response.jsonPath().get("Message");

        Assertions.assertEquals(ErrorMessages.JWT_MISSING.getMessage(), actual);
        Assertions.assertEquals(401, response.statusCode());
    }

    @Test
    @Description("Get list of characters")
    public void checkListCharacters() {
        Response response = Request.get(endpoint);
        System.out.println(response.asString());
        String message = response.jsonPath().get("Message");


        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Description("Get character by ID")
    public void checkCurrentCharacter() {
        Response response = Request.get(endpoint + "/1");
        response.body().prettyPrint();
        String alias = response.jsonPath().getString("alias");

        Assertions.assertEquals("Wolverine", alias);
        Assertions.assertEquals(200, response.statusCode());
    }

    @Test
    @Description("Create a new character")
    public void createNewUser() {
        CharacterRequest character = RandomGenerateCharacter.createCharacter();
        String alias = character.alias();

        Response response = Request.post(character, endpoint + "/add");
        String actual = response.jsonPath().getString("Message");

        Assertions.assertEquals(ErrorMessages.CHARACTER_ADDED.getMessage(alias), actual);
        Assertions.assertEquals(200, response.statusCode());
    }
}
