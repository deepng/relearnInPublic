package restAssured.apis;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.response.Response;
import restAssured.PutApi;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReviewProductApi extends PutApi {

    String userId;
    String review;

    public ReviewProductApi(String baseUrl) {
        super(baseUrl);
    }

    @Override
    protected void buildBody() {
        String body = "{\"message\": \"" + review + "\"," +
                "\"author\": \"" + userId + "\"}";
        requestSpecBuilder.setBody(body);
    }

    @Override
    protected void setHeaders() {
        requestSpecBuilder.addHeader("Content-Type", "application/json");
        requestSpecBuilder.addHeader("Authorization", "Bearer " + cookie);
    }

    public void putReviewFor(@NotNull String cookie,
                             @NotNull int productId,
                             @NotNull String review,
                             @NotNull String userId) {
        this.review = review;
        this.userId = userId;
        this.cookie = cookie;
        buildBody();
        setHeaders();
        requestSpecBuilder.setBaseUri(baseUrl);
        setPath(String.format("/rest/products/%s/reviews", productId));
        requestSpecBuilder.setBasePath(path);

        Response response = given().spec(requestSpecBuilder.build())
                .put(baseUrl + String.format(path, productId));

        response.then()
        .statusCode(201) // This is the given statusCode in the response
        .body("staus", equalTo("success")); // There is a typo for status in the API response

    }
}
