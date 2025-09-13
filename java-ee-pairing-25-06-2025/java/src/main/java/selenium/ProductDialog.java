package selenium;

import RestAssured.BaseApi;
import dev.failsafe.internal.util.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ProductDialog extends BasePage {

    public void addReviewComments(WebDriver driver, String review) {
        WebElement tagName = driver.findElement(By.tagName("textarea"));
        // can get the character limit and check
//        String maxlength = tagName.getDomAttribute("maxlength");// is 160
//        if(maxlength != null && review.length() > Integer.parseInt(maxlength)) {
//            Assert.isTrue(false,
//                    String.format("The review message size can't exceed %s characters", maxlength));
//        }
        tagName.sendKeys(review);
        WebElement submitButton = driver.findElement(By.id("submitButton"));
        submitButton.click();
    }

    public boolean isVisible(WebDriver driver) {
        WebElement dialogBox = getDialogBox(driver);
        return dialogBox.isDisplayed();
    }

    public WebElement getDialogBox(WebDriver driver) {
        return SeleniumUtils.findElementForTypeValue(SeleniumTypes.TAG_NAME,"mat-dialog-container", driver);
    }

    public void checkReviewsFor(WebDriver driver, String reviewComments, String userEmail) {
        WebElement reviewPanel = driver.findElement(By.tagName("mat-expansion-panel-header"));
        reviewPanel.click();
        String reviewCount =
                driver.findElement(By.xpath("//span[text()='Reviews']/following-sibling::span"))
                        .getText().replace("(","").replace(")","");
      List<WebElement> reviewText = driver.findElements(By.cssSelector(".mat-tooltip-trigger.review-text"));

        if(Integer.parseInt(reviewCount) == reviewText.size()) {
            for(WebElement review: reviewText) {
                String email = review.findElement(By.tagName("cite")).getText();
                if(userEmail.equalsIgnoreCase(email)) {
                    String comment = review.findElement(By.tagName("p")).getText();
                    if(comment.contains(reviewComments))
                        return; // We might have more than one comment from the test user
                }
            }
            Assert.isTrue(false,
                    "We didn't find the current users review or the in the given review comments - " + reviewComments);
        } else {
            Assert.isTrue(false,
                    String.format("We were expecting %s reviews, but got %d reviews", reviewCount, reviewText.size()));
        }
    }
}
