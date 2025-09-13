package restAssured.apis;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ReviewProductApi extends PostApi {

    String userId;
    String review;

    public ReviewProductApi(String baseUrl) {
        super(baseUrl);
        setPath("/rest/products/%s/reviews");
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

    public void postReviewfor(@NotNull String cookie,
                              @NotNull int productId,
                              @NotNull String review,
                              @NotNull String userId) {
        this.review = review;
        this.userId = userId;
        this.cookie = cookie;
        buildBody();
        setHeaders();
        Response response = execute();
        given().spec(requestSpecBuilder.build())
                .post(baseUrl + String.format(path, productId))
                .then()
                .statusCode(200)
                .body("status", equalTo("success"));
    }
}
