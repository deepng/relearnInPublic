package restAssured.apis;

import io.restassured.response.Response;
import models.ProductReviews;

import static io.restassured.RestAssured.given;

public class CheckProductReviewApi extends GetApi {
    public CheckProductReviewApi(String baseUrl) {
        super(baseUrl);
    }


    public boolean validateThisProductReview(String cookie, int productId, String reviewComments, String email) {
        Response response = given()
                .basePath(String.format("/rest/products/%s/reviews", productId))
                .baseUri(baseUrl)
                .header("Content-Type", "application/json")
                .and()
                .header("Authorization", "Bearer " + cookie)
                .get();

        if(response.getStatusCode() == 200) {
            ProductReviews reviews = response.then().extract().body().as(ProductReviews.class);
            for(ProductReviews.Data data: reviews.getData()) {
                if(data.getProduct() == productId && data.getAuthor().equalsIgnoreCase(email)) {
                    if(data.getMessage().contains(reviewComments))
                        return true;
                }
            }
        }
        return false;
    }
}
