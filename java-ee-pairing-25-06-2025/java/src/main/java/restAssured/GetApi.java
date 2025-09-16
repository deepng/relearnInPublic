package restAssured;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class GetApi extends BaseApi {

    public GetApi(String baseUrl) {
        super(baseUrl);
    }


    protected Response execute() {
        setHeaders();
        RequestSpecification requestSpecification = generateRequestSpec();
        return given().spec(requestSpecification).post();
    }
}
