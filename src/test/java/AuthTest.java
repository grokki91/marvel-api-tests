import api.Request;
import data.DataGenerator;
import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.ErrorMessages;

@Owner("QA")
public class AuthTest {

    @Test
    @Description("User registration with valid data")
    public void successRegistration() {
        RegisterRequest data = DataGenerator.createUser();

        Response res = Request.post(data, "/signup");
        String token = res.jsonPath().get("token");

        Assertions.assertNotNull(token);
        Assertions.assertEquals(200, res.statusCode());
    }

    @Test
    @Description("User registration with an existing email")
    public void failRegistrationExistEmail() {
        RegisterRequest existUser = DataGenerator.createUser();
        String existEmail = existUser.email();
        RegisterRequest newUser = DataGenerator.createUserExceptEmail(existEmail);

        Request.post(existUser, "/signup");
        Response res = Request.post(newUser, "/signup");

        String actual = res.jsonPath().getString("Message");

        Assertions.assertEquals(ErrorMessages.EMAIL_EXIST.getMessage(existEmail), actual);
        Assertions.assertEquals(409, res.statusCode());
    }

    @Test
    @Description("User registration with an existing username")
    public void failRegistrationExistUsername() {
        RegisterRequest existUser = DataGenerator.createUser();
        String existUsername = existUser.username();
        RegisterRequest newUser = DataGenerator.createUserExceptUsername(existUsername);

        Request.post(existUser, "/signup");
        Response res = Request.post(newUser, "/signup");

        String actual = res.jsonPath().getString("Message");

        Assertions.assertEquals(ErrorMessages.USERNAME_EXIST.getMessage(existUsername), actual);
        Assertions.assertEquals(409, res.statusCode());
    }

    @Test
    @Description("User login with valid data")
    public void successLogin() {
        RegisterRequest existUser = DataGenerator.createUser();
        String username = existUser.username();
        String password = existUser.password();

        Request.post(existUser, "/signup");
        LoginRequest data = new LoginRequest(username, password);

        Response res = Request.post(data, "/login");

        Assertions.assertNotNull(res.jsonPath().get("token"));
        Assertions.assertEquals(200, res.statusCode());
    }

    @Test
    @Description("Check login with empty field")
    public void checkNotSuccessLogin() {
        LoginRequest data = new LoginRequest("test", "");

        Response res = Request.post(data, "/login");

        String actual = res.jsonPath().getString("Message");

        Assertions.assertEquals(ErrorMessages.INVALID_CREDENTIALS.getMessage(), actual);
        Assertions.assertEquals(401, res.statusCode());
    }
}
