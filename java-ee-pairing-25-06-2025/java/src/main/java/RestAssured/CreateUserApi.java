package RestAssured;

import RestAssured.models.Customer;
import RestAssured.models.NewUserModel;
import RestAssured.models.SecurityQuestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.Utils;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CreateUserApi extends PostApi{

    public CreateUserApi(String baseUrl) {
        super(baseUrl);
    }

    @Override
    protected void buildBody() {

    }

    @Override
    protected void setHeaders() {

    }

    public String createNewUser(Customer customer) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        NewUserModel newUser = new NewUserModel();
        newUser.setEmail(customer.getEmail());
        newUser.setPassword(customer.getPassword());
        newUser.setPasswordRepeat(customer.getPassword());
        SecurityQuestion securityQuestion =
                objectMapper.readValue(getClass().getClassLoader().getResourceAsStream("SecurityQuestion.json"),
                SecurityQuestion.class);
        String currentDateInFormat = Utils.getCurrentDateInFormat(Utils.DATE_FORMAT);
        securityQuestion.setCreatedAt(currentDateInFormat);
        securityQuestion.setUpdatedAt(currentDateInFormat);
        newUser.setSecurityQuestion(securityQuestion);
        newUser.setSecurityAnswer(customer.getSecurityAnswer());

        String status = given()
                .header("Content-Type", "application/json")
                .body(newUser)
                .post(baseUrl + "/api/Users")
                .then()
                .extract()
                .path("status");

        return status;
    }


}
