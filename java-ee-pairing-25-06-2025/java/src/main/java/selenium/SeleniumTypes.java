package selenium;

public enum SeleniumTypes {
    ID("id"),
    NAME("name"),
    XPATH("xpath"),
    CSS("css"),
    CLASS_NAME("className"),
    TAG_NAME("tagName"),
    LINK_TEXT("linkText"),
    PARTIAL_LINK_TEXT("partialLinkText"),
    TEXT("text"),
    PARTIAL_TEXT("partialText");

    String key;

    SeleniumTypes(String text) {
        this.key = text;
    }
}
