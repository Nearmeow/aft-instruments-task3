package com.mariakh.tests.base;

import com.mariakh.framework.managers.DriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class BaseTests {

    private DriverManager driverManager = DriverManager.getInstance();

    @BeforeEach
    public void before() {
        driverManager.getDriver().get("https://www.dns-shop.ru/");
    }

    @AfterEach
    public void after() {
       driverManager.getDriver().quit();
    }
}
