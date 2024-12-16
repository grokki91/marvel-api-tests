import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class AuthTest {
    private final String mesInvalidCredentials = "Invalid credentials";
    private final String mesUsernameExist= "User with USERNAME=user already exists";
    private final String mesEmailExist= "User with EMAIL=user@te.st already exists";

    @Test
    @Description("Check success registration")
    public void successRegistration() {
        Specification.init(Specification.request(), Specification.resStatus(200));

        RegisterRequest data = new RegisterRequest(
                "user",
                "user",
                "user@example.com",
                "Male",
                "1990-01-01"
        );

        Response res = given()
                .when()
                .body(data)
                .post("/signup");

        Assertions.assertNotNull(res.jsonPath().get("token"));
    }

    @Test
    @Description("Check registration with exist email")
    public void failRegistrationExistEmail() {
        Specification.init(Specification.request(), Specification.resStatus(409));

        RegisterRequest data = new RegisterRequest(
                "sfdfsd",
                "123",
                "user@te.st",
                "Male",
                "1990-01-01"
        );

        Response res = given()
                .when()
                .body(data)
                .post("/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(mesEmailExist, actualMessage);
    }

    @Test
    @Description("Check registration with exist username")
    public void failRegistrationExistUsername() {
        Specification.init(Specification.request(), Specification.resStatus(409));

        RegisterRequest data = new RegisterRequest(
                "user",
                "123",
                "user123@example.com",
                "Male",
                "1990-01-01"
        );

        Response res = given()
                .when()
                .body(data)
                .post("/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(mesUsernameExist, actualMessage);
    }

    @Test
    @Description("Check success login")
    public void successLogin() {
        Specification.init(Specification.request(), Specification.resStatus(200));

        LoginRequest data = new LoginRequest("user", "user");

        Response res = given()
                .when()
                .body(data)
                .post("/login");

        Assertions.assertNotNull(res.jsonPath().get("token"));
    }

    @Test
    @Description("Check login with empty field")
    public void checkNotSuccessLogin() {
        Specification.init(Specification.request(), Specification.resStatus(401));

        LoginRequest data = new LoginRequest("test", "");

        Response res = given()
                .when()
                .body(data)
                .post("/login");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(mesInvalidCredentials, actualMessage);
    }
}
