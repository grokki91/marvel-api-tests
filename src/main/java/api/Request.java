package api;

import dto.request.LoginRequest;
import dto.request.RegisterRequest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Logger;
import utils.ReadProperties;

import static io.restassured.RestAssured.given;

public class Request {
    private static final String URL = ReadProperties.get("base_url");

    public static RequestSpecification baseRequest() {
        return new RequestSpecBuilder()
                .setBaseUri(URL)
                .setContentType(ContentType.JSON)
                .addFilter(new Logger())
                .build();
    }

    public static Response postRequest(RegisterRequest data, String endpoint) {
        return given()
                .spec(baseRequest())
                .when()
                .body(data)
                .post(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }

    public static Response postRequest(LoginRequest data, String endpoint) {
        return given()
                .spec(baseRequest())
                .when()
                .body(data)
                .post(endpoint)
                .then()
                .log().ifValidationFails()
                .extract().response();
    }
}
