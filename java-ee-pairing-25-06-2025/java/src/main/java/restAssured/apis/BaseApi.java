package restAssured.apis;

import io.restassured.response.Response;

public abstract class BaseApi {
    protected String baseUrl;
    protected String path;
    protected String cookie;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
