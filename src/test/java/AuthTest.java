import data.AuthData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

public class AuthTest {
    private final String URL = "http://185.128.107.32:8080/";

    @Test
    public void checkSuccessRegistration() {
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/register")
                .then().log().all()
                .extract().body().jsonPath().getList("data", AuthData.class);
    }

    @Test
    public void checkSuccessLogin() {
        RestAssured.given()
                .when()
                .contentType(ContentType.JSON)
                .get(URL + "/login")
                .then().log().all()
                .extract().body().jsonPath().getList("data", AuthData.class);
    }
}
