package selenium;

import org.openqa.selenium.WebDriverException;

public class SeleniumCustomException extends Throwable {
    public SeleniumCustomException(String s) {
        super(s);
    }

    public SeleniumCustomException(Throwable cause) {
        super(cause);
    }

    public SeleniumCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public SeleniumCustomException() {
        super();
    }

    @Override
    public String getMessage() {
        return getCause() instanceof WebDriverException
                ? super.getMessage()
                : createMessage(super.getMessage());
    }

    private String createMessage(String message) {
        return "SeleniumCustomException: " + message;
    }
}
