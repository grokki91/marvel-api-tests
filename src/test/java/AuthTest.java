import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthTest {
    private final String URL = "http://185.128.107.32:8080";
    private final String mesInvalidCredentials = "Invalid credentials";
    private final String mesUsernameExist= "User with USERNAME=user already exists";
    private final String mesEmailExist= "User with EMAIL=user@te.st already exists";

    @Test
    public void successRegistration() {
        RegisterRequest data = new RegisterRequest(
                "user",
                "user",
                "user@example.com",
                "Male",
                "1990-01-01"
        );

        Response res = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post(URL + "/signup");


        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertNotNull(res.jsonPath().get("token"));
    }

    @Test
    public void failRegistrationExistEmail() {
        RegisterRequest data = new RegisterRequest(
                "user12345",
                "123",
                "user@te.st",
                "Male",
                "1990-01-01"
        );

        Response res = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post(URL + "/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(409, res.statusCode());
        Assertions.assertEquals(mesEmailExist, actualMessage);
    }

    @Test
    public void failRegistrationExistUsername() {
        RegisterRequest data = new RegisterRequest(
                "user",
                "123",
                "user123@example.com",
                "Male",
                "1990-01-01"
        );

        Response res = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post(URL + "/signup");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(409, res.statusCode());
        Assertions.assertEquals(mesUsernameExist, actualMessage);
    }

    @Test
    public void successLogin() {
        LoginRequest data = new LoginRequest("user", "user");

        Response res = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post(URL + "/login");

        Assertions.assertEquals(200, res.statusCode());
        Assertions.assertNotNull(res.jsonPath().get("token"));
    }

    @Test
    public void checkNotSuccessLogin() {
        LoginRequest data = new LoginRequest("admin", "");

        Response res = RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .body(data)
                .post(URL + "/login");

        String actualMessage = res.jsonPath().getString("Message");
        Assertions.assertEquals(401, res.statusCode());
        Assertions.assertEquals(mesInvalidCredentials, actualMessage);
    }
}
