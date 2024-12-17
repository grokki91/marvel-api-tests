import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.*;

public class Specification {
    private static final String URL = "http://185.128.107.32:8080";

    public static RequestSpecification request() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static Response postRequest(RegisterRequest data, String endpoint) {
        return given()
                .spec(request())
                .when()
                .body(data)
                .post(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public static Response postRequest(LoginRequest data, String endpoint) {
        return given()
                .spec(request())
                .when()
                .body(data)
                .post(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public static ResponseSpecification resStatus(int status) {
        return new ResponseSpecBuilder()
                .expectStatusCode(status)
                .build();
    }
}
