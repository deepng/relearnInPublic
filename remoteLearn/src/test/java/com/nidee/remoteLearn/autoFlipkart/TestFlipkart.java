package com.nidee.remoteLearn.autoFlipkart;

import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestFlipkart extends BaseTest {
    FlipkartHome flipkartHome;


    @BeforeClass
    public void classSetup() {
        flipkartHome = new FlipkartHome(driver);

    }


    @Test
    public void testFlipkart(ITestContext testContext) {
        flipkartHome.checkHomePage();
    }

}
