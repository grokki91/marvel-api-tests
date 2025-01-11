import api.Request;
import config.BaseSetting;
import data.RandomGenerateCharacter;
import dto.request.CharacterRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.CustomAssert;
import utils.ErrorMessages;

@Owner("QA")
public class CharacterTest extends BaseSetting {
    private final String endpoint = "/api/characters";

    @Test
    @DisplayName("Test-case 1: Send request without jwt in header")
    public void checkJwtMissing() {
        Response response = Request.getWithoutAuth(endpoint);
        String actual = response.jsonPath().get("Message");

        CustomAssert.equals(ErrorMessages.JWT_MISSING.getMessage(), actual);
        CustomAssert.equals(401, response.statusCode());
    }

    @Test
    @DisplayName("Test-case 2: Get list of characters")
    public void checkListCharacters() {
        Response response = Request.get(endpoint);

        CustomAssert.equals(200, response.statusCode());
    }

    @Test
    @DisplayName("Test-case 3: Get character by ID")
    public void checkCurrentCharacter() {
        Response response = Request.get(endpoint + "/1");
        response.body().prettyPrint();
        String alias = response.jsonPath().getString("alias");

        CustomAssert.equals("Wolverine", alias);
        CustomAssert.equals(200, response.statusCode());
    }

    @Test
    @DisplayName("Test-case 4: Create a new character")
    public void createNewUser() {
        CharacterRequest character = RandomGenerateCharacter.createCharacter();
        String alias = character.alias();

        Response response = Request.post(character, endpoint + "/add");
        String actual = response.jsonPath().getString("Message");

        CustomAssert.equals(ErrorMessages.CHARACTER_ADDED.getMessage(alias), actual);
        CustomAssert.equals(200, response.statusCode());
    }
}
