package com.nidee.remoteLearn.autoFlipkart;

import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestFlipkart extends BaseTest {
    FlipkartHome flipkartHome;
    SearchPage searchPage;

    @BeforeClass
    public void classSetup() {
        flipkartHome = new FlipkartHome(driver);
        searchPage = new SearchPage(driver);
    }


    @Test
    public void testFlipkart(ITestContext testContext) {
        Assert.assertTrue(flipkartHome.checkHomePage());
        flipkartHome.searchProductAndSelectFromSuggestions("macbook", "air");
        Assert.assertTrue(searchPage.checkIfInSearchPage());
    }

}
