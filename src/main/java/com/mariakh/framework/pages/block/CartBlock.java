package com.mariakh.framework.pages.block;

import com.mariakh.framework.model.Product;
import com.mariakh.framework.pages.BasePage;
import com.mariakh.framework.pages.CartPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Map;

public class CartBlock extends BasePage {

    @FindBy(xpath = "//div[@class='cart-button']")
    private WebElement cartButton;

    @FindBy(xpath = "//span[contains(@class, 'cart-link-counter__price')]")
    private WebElement totalAmount;

    public CartBlock() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public void checkTotalAmount() {
        Assertions.assertEquals(parseStringToInt(totalAmount.getText()), calcTotalAmountInMap());
    }

    public void clickOnCart() {
        cartButton.click();
    }

    public void checkCartPrice() {
        WebElement price = cartButton.findElement(By.xpath(".//span[contains(@class, 'buttons__link-price')]"));

    }


}
