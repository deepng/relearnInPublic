package restAssured.apis;

import org.openqa.selenium.Cookie;
import restAssured.models.Products;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductListApi extends GetApi {
    public ProductListApi(String baseUrl) {
        super(baseUrl);
        setPath("/rest/products/search?q=");
    }

    public Products getProductList(String cookieValue) {
        Products products = given()
                .header("Content-Type", "application/json")
                .and()
                .header("Authorization", "Bearer " + cookieValue)
                .when()
                .get(baseUrl + path)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .as(Products.class);
        return products;
    }

}
