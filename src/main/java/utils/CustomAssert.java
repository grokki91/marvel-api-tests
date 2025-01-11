package utils;

import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomAssert {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogger.class);

    public static void equals(Object expected, Object actual) {
        try {
            Assertions.assertEquals(expected, actual);
        } catch (AssertionError error) {
            FilterableRequestSpecification request = CustomLogger.getRequest();
            Response response = CustomLogger.getResponse();
            logger(request, response);
            throw error;
        }
    }

    private static void logger(FilterableRequestSpecification req, Response res) {
        logger.error("Request Method: {}", req.getMethod());
        logger.error("Request URL: {}", req.getURI());

        if (req.getBody() != null) {
            logger.error("Request Payload: {}", req.getBody().toString());
        }

        logger.error("Response Body: {}", res.getBody().asString());
    }
}
