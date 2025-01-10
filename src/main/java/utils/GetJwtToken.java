package utils;

import api.Request;
import data.RandomGenerateUser;
import dto.request.RegisterRequest;
import io.restassured.response.Response;

public class GetJwtToken {
    private static String token;

    public static String getToken() {
        if (token == null) {
            token = generateToken();
        }

        return token;
    }

    private static String generateToken() {
        RegisterRequest data = RandomGenerateUser.createUser();

        Response res = Request.post(data, "/signup");

        if (res.statusCode() != 200) {
            throw new RuntimeException("Token wasn't received");
        }

        return res.jsonPath().get("token");
    }
}
