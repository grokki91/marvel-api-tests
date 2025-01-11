package utils;

import io.qameta.allure.Allure;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class CustomLogger implements Filter {
    private static  ThreadLocal<FilterableRequestSpecification> requestThread = new ThreadLocal<>();
    private static  ThreadLocal<Response> responseThread = new ThreadLocal<>();

    public static FilterableRequestSpecification getRequest() {
        return requestThread.get();
    }

    public static Response getResponse() {
        return responseThread.get();
    }

    @Override
    public Response filter(FilterableRequestSpecification reqSpec, FilterableResponseSpecification resSpec, FilterContext context) {
        Response response = context.next(reqSpec, resSpec);
        requestThread.set(reqSpec);
        responseThread.set(response);

        logRequest(reqSpec);
        logResponse(response);

        return response;
    }

    private void logRequest(FilterableRequestSpecification req) {
        String uri = req.getURI();
        String method = req.getMethod();
        String payload = req.getBody();

        String requestData = String.format(
                "URL: %s\nMethod: %s\nPayload: %s\n",
                uri, method, payload
        );

        Allure.getLifecycle().addAttachment(
                "Request",
                "text/plain",
                ".txt",
                new ByteArrayInputStream(requestData.getBytes(StandardCharsets.UTF_8))
        );
    }

    private void logResponse(Response res) {
        String body = res.getBody().asPrettyString();
        int statusCode = res.getStatusCode();

        String responseData = String.format(
                "StatusCode: %d\nBody: %s\n",
                statusCode, body
        );

        Allure.getLifecycle().addAttachment(
                "Response",
                "text/plain",
                ".txt",
                new ByteArrayInputStream(responseData.getBytes(StandardCharsets.UTF_8))
        );
    }
}
