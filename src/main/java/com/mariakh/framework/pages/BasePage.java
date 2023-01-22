package com.mariakh.framework.pages;

import com.mariakh.framework.managers.DriverManager;
import com.mariakh.framework.managers.PageManager;
import com.mariakh.framework.managers.ProductManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));
    protected JavascriptExecutor js = (JavascriptExecutor) driverManager.getDriver();
    protected PageManager pageManager = PageManager.getInstance();
    protected ProductManager productCollection = ProductManager.getInstance();

    public BasePage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    protected String cleanString(String string) {
        if (string.contains("₽")) {
            int roubleSignIndex = string.indexOf("₽");
            String valueBeforeRouble = string.substring(0, roubleSignIndex);
            return valueBeforeRouble.replaceAll("\\D+","");
        }
        return string.replaceAll("\\D+","");
    }

    protected int parsePriceToInt(String string) {
        return Integer.parseInt(cleanString(string));
    }

    protected WebElement scrollToElementJs(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    protected void scrollToPageTop() {
        js.executeScript("window.scrollTo(0, document.body.scrollTop);");
    }

    protected void scrollToPageBottom() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }

}
