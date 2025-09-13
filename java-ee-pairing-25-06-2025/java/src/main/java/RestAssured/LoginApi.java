package RestAssured;

import io.restassured.response.Response;

public class LoginApi extends PostApi {

    private String user;
    private String password;

    public LoginApi(String baseUrl) {
        super(baseUrl);
        setPath("/rest/user/login");
    }

    public String loginAndGetStatus(String user, String password) {
        this.user = user;
        this.password = password;
        buildBody();
        Response response = execute();
        if(response.statusCode() == 200) {
            return response.path("authentication.token");
        }
        return ""; // Its an HTML response
    }

    protected void buildBody() {
        String payload = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", user, password) ;
        requestSpecBuilder.setBody(payload);
    }

    @Override
    protected void setHeaders() {
        requestSpecBuilder.addHeader("Content-Type", "application/json");
    }

}
