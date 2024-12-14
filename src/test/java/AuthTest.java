import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class AuthTest {
    private final String mesInvalidCredentials = "Invalid credentials";
    private final String mesUsernameExist= "User with USERNAME=user already exists";
    private final String mesEmailExist= "User with EMAIL=user@te.st already exists";

    @Test
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
    public void checkNotSuccessLogin() {
        Specification.init(Specification.request(), Specification.resStatus(401));

        LoginRequest data = new LoginRequest("admin", "");

        Response res = given()
                .when()
                .body(data)
                .post("/login");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(mesInvalidCredentials, actualMessage);
    }
}
