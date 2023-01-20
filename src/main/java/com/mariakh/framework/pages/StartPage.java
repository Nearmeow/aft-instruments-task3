package com.mariakh.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class StartPage {

    @FindBy(xpath = "//input[@class='presearch__input']")
    private WebElement searchField;

    @FindBy(xpath = "//span[@class='presearch__icon-search']")
    private WebElement loupButton;

    public void searchProduct(String product) {
        searchField.sendKeys(product);
        loupButton.click();
    }
}
