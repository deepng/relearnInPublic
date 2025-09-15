package ee.testng;

import com.fasterxml.jackson.core.JsonProcessingException;
import helpers.TestDataReader;
import models.Products;
import models.UserData;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import restAssured.apis.ProductListApi;

import java.util.Optional;

public class ApiTests extends BaseTest {
    private String cookie;
    ProductListApi productListApi;


    @BeforeClass
    public void classSetup() throws JsonProcessingException {
        Optional<UserData> testData = TestDataReader.getInstance().getFirstUser();
        testData.ifPresent(userData -> cookie = loginAndCaptureCookie(userData.userId, userData.password));
        productListApi = new ProductListApi(baseUrl);
    }

    @Test(invocationCount = 100, threadPoolSize = 5, groups = {"load"})
    public void loadTestProductList() {
        Products productList = productListApi.getProductList(cookie);
        Assert.assertFalse(productList.getData().isEmpty(), "We didn't get the product list");
    }
}
