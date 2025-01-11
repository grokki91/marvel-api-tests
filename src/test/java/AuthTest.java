import api.Request;
import data.RandomGenerateUser;
import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utils.CustomAssert;
import utils.ErrorMessages;

@Owner("QA")
public class AuthTest {

    @Test
    @DisplayName("Test-case 5: User registration with valid data")
    public void successRegistration() {
        RegisterRequest data = RandomGenerateUser.createUser();

        Response res = Request.post(data, "/signup");
        String token = res.jsonPath().get("token");

        Assertions.assertNotNull(token);
        CustomAssert.equals(200, res.statusCode());
    }

    @Test
    @DisplayName("Test-case 6: User registration with an existing email")
    public void failRegistrationExistEmail() {
        RegisterRequest existUser = RandomGenerateUser.createUser();
        String existEmail = existUser.email();
        RegisterRequest newUser = RandomGenerateUser.createUserExceptEmail(existEmail);

        Request.post(existUser, "/signup");
        Response res = Request.post(newUser, "/signup");

        String actual = res.jsonPath().getString("Message");

        CustomAssert.equals(ErrorMessages.EMAIL_EXIST.getMessage(existEmail), actual);
        CustomAssert.equals(409, res.statusCode());
    }

    @Test
    @DisplayName("Test-case 7: User registration with an existing username")
    public void failRegistrationExistUsername() {
        RegisterRequest existUser = RandomGenerateUser.createUser();
        String existUsername = existUser.username();
        RegisterRequest newUser = RandomGenerateUser.createUserExceptUsername(existUsername);

        Request.post(existUser, "/signup");
        Response res = Request.post(newUser, "/signup");

        String actual = res.jsonPath().getString("Message");

        CustomAssert.equals(ErrorMessages.USERNAME_EXIST.getMessage(existUsername), actual);
        CustomAssert.equals(409, res.statusCode());
    }

    @Test
    @DisplayName("Test-case 8: User login with valid data")
    public void successLogin() {
        RegisterRequest existUser = RandomGenerateUser.createUser();
        String username = existUser.username();
        String password = existUser.password();

        Request.post(existUser, "/signup");
        LoginRequest data = new LoginRequest(username, password);

        Response res = Request.post(data, "/login");

        Assertions.assertNotNull(res.jsonPath().get("token"));
        CustomAssert.equals(200, res.statusCode());
    }


    @ParameterizedTest(name = "Test-case 9: User login with empty field (username = \"{0}\", password = \"{1}\")")
    @CsvFileSource(resources = "data/login_failed.csv", numLinesToSkip = 1)
    public void checkNotSuccessLogin(String username, String password) {
        LoginRequest data = new LoginRequest(username, password);

        Response res = Request.post(data, "/login");

        String actual = res.jsonPath().getString("Message");

        CustomAssert.equals(ErrorMessages.INVALID_CREDENTIALS.getMessage(), actual);
        CustomAssert.equals(401, res.statusCode());
    }
}
