package restAssured.apis;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class PostApi extends BaseApi {

    RequestSpecBuilder requestSpecBuilder;


    PostApi(String baseUrl) {
        super(baseUrl);
        requestSpecBuilder = new RequestSpecBuilder();
    }

    protected abstract void buildBody() ;


    protected Response execute() {
        setHeaders();
        RequestSpecification requestSpecification = generateRequestSpec();
        return given().spec(requestSpecification).post();
    }

    protected abstract void setHeaders();

    private RequestSpecification generateRequestSpec() {
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setBaseUri(baseUrl);
        return requestSpecBuilder.build();
    }
}
