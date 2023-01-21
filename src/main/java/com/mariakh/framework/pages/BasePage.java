package com.mariakh.framework.pages;

import com.mariakh.framework.managers.DriverManager;
import com.mariakh.framework.managers.PageManager;
import com.mariakh.framework.managers.ProductManager;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));
    protected PageManager pageManager = PageManager.getInstance();
    protected ProductManager productCollection = ProductManager.getInstance();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected String parseToStringOnlyDigits(String string) {
        return string.replaceAll("\\D+","");
    }

    protected int parseStringToInt(String string) {
        return Integer.parseInt(parseToStringOnlyDigits(string));
    }

}
