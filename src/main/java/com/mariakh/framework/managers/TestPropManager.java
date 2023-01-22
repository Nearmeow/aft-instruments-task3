package com.mariakh.framework.managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class TestPropManager {

    private final Properties appProperties = new Properties();
    private final Properties testData = new Properties();
    private static TestPropManager instance;


    private TestPropManager() {
        loadApplicationProperties();
        loadTestData();
    }

    public static TestPropManager getInstance() {
        if (instance == null) {
            instance = new TestPropManager();
        }
        return instance;
    }

    public String getProperty(String key, String defaultValue) {
        return appProperties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return appProperties.getProperty(key);
    }

    public String getTestData(String key, String defaultValue) {
        return testData.getProperty(key, defaultValue);
    }

    public String getTestData(String key) {
        return testData.getProperty(key);
    }

    private void loadApplicationProperties() {
        String fileName = System.getProperty("propFile", "app");
        try {
            appProperties.load(new FileInputStream("src/main/resources/" + fileName + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadTestData() {
        try {
            testData.load(new FileInputStream("src/test/resources/test.data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
