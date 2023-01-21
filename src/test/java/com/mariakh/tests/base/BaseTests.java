package com.mariakh.tests.base;

import com.mariakh.framework.managers.DriverManager;
import com.mariakh.framework.managers.InitManager;
import com.mariakh.framework.managers.PageManager;
import com.mariakh.framework.managers.TestPropManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaseTests {

    protected PageManager pageManager = PageManager.getInstance();
    protected TestPropManager propManager = TestPropManager.getInstance();

    @BeforeAll
    public static void beforeAll() {
        InitManager.initFramework();
    }
    @BeforeEach
    public void before() {
        DriverManager.getInstance().getDriver().get(propManager.getProperty("base.url"));
    }

    @AfterAll
    public static void afterAll() {
        InitManager.quitFramework();
    }
}
