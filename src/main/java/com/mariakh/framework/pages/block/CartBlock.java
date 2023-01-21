package com.mariakh.framework.pages.block;

import com.mariakh.framework.pages.BasePage;
import com.mariakh.framework.pages.CartPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartBlock extends BasePage {

    @FindBy(xpath = "//div[@class='cart-button']")
    private WebElement cartButton;

    @FindBy(xpath = "//span[contains(@class, 'cart-link-counter__price')]")
    private WebElement totalAmount;

    @FindBy(xpath = "//span[@class='cart-link-counter__badge']")
    private WebElement productsInCartCount;

    public void checkTotalAmount() {
        Assertions.assertEquals(parseStringToInt(totalAmount.getText()), productCollection.getTotalAmountOfSavedProducts());
    }

    public Integer productsInCartCount() {
        return parseStringToInt(productsInCartCount.getText());
    }

    public CartPage clickOnCart() {
        cartButton.click();
        return pageManager.getCartPage();
    }

}
