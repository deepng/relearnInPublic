package restAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class PutApi extends BaseApi {

    public RequestSpecBuilder requestSpecBuilder;


    public PutApi(String baseUrl) {
        super(baseUrl);
        requestSpecBuilder = new RequestSpecBuilder();
    }

    protected abstract void buildBody() ;


    protected Response execute() {
        setHeaders();
        buildBody();
        RequestSpecification requestSpecification = generateRequestSpec();
        return given().spec(requestSpecification).put();
    }

    protected abstract void setHeaders();

}
