package com.mariakh.framework.pages.block;

import com.mariakh.framework.pages.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchBlock extends BasePage {

    @FindBy(xpath = "//input[@class='presearch__input']")
    private WebElement searchField;

    @FindBy(xpath = "//span[@class='presearch__icon-search']")
    private WebElement loupButton;

    public SearchBlock() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public void searchProduct(String product) {
        searchField.sendKeys(product);
        loupButton.click();
    }
}
