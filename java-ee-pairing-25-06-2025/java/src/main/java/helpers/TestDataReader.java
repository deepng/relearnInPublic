package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import models.UserData;

import java.util.*;

public class TestDataReader {
    private static Map<String, String> testData;

    private TestDataReader() {}

    public static TestDataReader getInstance() {
        return InstanceHolder.testDataInstance;
    }

    public Map<String, String> getTestData() {
        if(testData == null)
            setUpTestData();
        return testData;
    }

    public void setUpTestData() {
        // load test data from properties file
        testData = new HashMap<>();
        ResourceBundle bundle = ResourceBundle.getBundle("test");//.getKeys().collectEntries { key -> [ (key) : ResourceBundle.getBundle('testData.json').getString(key) ] }
        bundle.keySet().forEach(key -> {
            testData.put(key, bundle.getString(key));
        });
        Properties properties = System.getProperties();
        for(String key : properties.stringPropertyNames()) {
            // if key starts with "test.", usually used to differentiate system properties
            if(key.startsWith("test."))
                testData.put(key.replaceFirst("test.", ""), properties.getProperty(key));
        }
    }

    public Optional<UserData> getFirstUser() throws JsonProcessingException {
        String userJsonArray = testData.get("users");

        ObjectMapper objectMapper = new ObjectMapper();
        List<UserData> userList = objectMapper.readValue(userJsonArray, new TypeReference<List<UserData>>() {});
        for(UserData user: userList){
            if(user.id == 1)
                return Optional.of(user);
        }
        return Optional.empty();
    }

    public String getBrowser() {
        return TestDataReader.getInstance().getTestData().get("browser");
    }

    public String getBaseUrl() {
        return TestDataReader.getInstance().getTestData().get("baseUrl");
    }

    public void addToTestData(@NotNull  String key, @NotNull String value) {
        testData.put(key, value);
    }

    private static class InstanceHolder {
        private static final TestDataReader testDataInstance = new TestDataReader();
    }
}
