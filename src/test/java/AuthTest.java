import data.DataGenerator;
import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthTest {
    private final String mesInvalidCredentials = "Invalid credentials";
    private final String mesUsernameExist= "User with USERNAME=user already exists";
    private final String mesEmailExist= "User with EMAIL=user@te.st already exists";

    @Test
    @Description("Check success registration")
    public void successRegistration() {
        RegisterRequest data = DataGenerator.createUser();

        Response res = Specification.postRequest(data, "/signup");
        String token = res.jsonPath().get("token");

        Assertions.assertNotNull(token, "Token is empty");
    }

    @Test
    @Description("Check registration with exist email")
    public void failRegistrationExistEmail() {
        RegisterRequest existUser = DataGenerator.createUser();
        String existEmail = existUser.email();
        RegisterRequest newUser = DataGenerator.createUserExceptEmail(existEmail);

        Specification.postRequest(existUser, "/signup");
        Response res = Specification.postRequest(newUser, "/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertTrue(actualMessage.contains(existEmail), "Error message should contain the email: " + existEmail);
    }

    @Test
    @Description("Check registration with exist username")
    public void failRegistrationExistUsername() {
        RegisterRequest existUser = DataGenerator.createUser();
        String existUsername = existUser.username();
        RegisterRequest newUser = DataGenerator.createUserExceptUsername(existUsername);

        Specification.postRequest(existUser, "/signup");
        Response res = Specification.postRequest(newUser, "/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertTrue(actualMessage.contains(existUsername), "Error message should contain the username: " + existUsername);
    }

    @Test
    @Description("Check success login")
    public void successLogin() {
        RegisterRequest existUser = DataGenerator.createUser();
        String username = existUser.username();
        String password = existUser.password();

        Specification.postRequest(existUser, "/signup");
        LoginRequest data = new LoginRequest(username, password);

        Response res = Specification.postRequest(data, "/login");

        Assertions.assertNotNull(res.jsonPath().get("token"));
    }

    @Test
    @Description("Check login with empty field")
    public void checkNotSuccessLogin() {
        LoginRequest data = new LoginRequest("test", "");

        Response res = Specification.postRequest(data, "/login");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(mesInvalidCredentials, actualMessage);
    }
}
