package RestAssured;

import io.restassured.response.Response;
import org.openqa.selenium.devtools.v85.fetch.model.AuthChallengeResponse;

public abstract class BaseApi {
    protected String baseUrl;
    protected String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    BaseApi(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    protected abstract Response execute();
}
