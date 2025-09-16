package restAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {
    protected String baseUrl;
    protected String path;
    protected String cookie;

    protected RequestSpecBuilder requestSpecBuilder;

    protected abstract void setHeaders();

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RequestSpecification generateRequestSpec() {
        requestSpecBuilder.setBasePath(path);
        requestSpecBuilder.setBaseUri(baseUrl);
        return requestSpecBuilder.build();
    }
}
