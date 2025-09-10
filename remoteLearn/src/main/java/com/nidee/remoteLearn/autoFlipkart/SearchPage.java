package com.nidee.remoteLearn.autoFlipkart;

import org.openqa.selenium.WebDriver;
import org.springframework.util.Assert;

import java.util.Objects;

public class SearchPage extends BaseClass {

    SearchPage(WebDriver driver) {
        super(driver);
    }

    public boolean checkIfInSearchPage() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/search?");
    }
}
