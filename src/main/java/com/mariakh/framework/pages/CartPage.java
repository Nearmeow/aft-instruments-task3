package com.mariakh.framework.pages;

import com.mariakh.framework.model.Product;
import com.mariakh.framework.pages.block.CartBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private CartBlock cartBlock = new CartBlock();

    @FindBy(xpath = "//div[@class='cart-items__product']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[@class='cart-tab-total-amount']")
    private WebElement totalAmountBlock;

    public CartPage checkProductPrices() {
        for (WebElement elem : productList) {
            int currentPrice = parsePriceToInt(elem.findElement(By.xpath(".//span[@class='price__current']")).getText());
            String currentCode = getCodeFromElement(elem);
            Product savedProduct = productCollection.getProductByCode(currentCode);
            Assertions.assertEquals(savedProduct.getPrice(), currentPrice, "Цена продукта с кодом "
                    + currentCode + " не соответствует ожидаемой.");
        }
        return pageManager.getCartPage();
    }

    public CartPage checkGuaranteePrices() {
        for (WebElement elem : productList) {
            WebElement checkedGuarantee = elem.findElement(By.xpath(".//div[contains(@class, 'checked')]"));
            String currentCode = getCodeFromElement(elem);
            int price = getGuaranteePrice(checkedGuarantee);
            Assertions.assertEquals(productCollection.getProductByCode(currentCode).getGuaranteePrice(), price);
        }
        return pageManager.getCartPage();
    }

    public CartPage checkTotalAmount() {
        WebElement currentPriceElement = totalAmountBlock.findElement(By.xpath(".//span[@class='price__current']"));
        int currentTotalPrice = parsePriceToInt(currentPriceElement.getText());
        Assertions.assertEquals(productCollection.getTotalAmountOfSavedProducts()
                , currentTotalPrice, "Общая стоимость всех товаров не совпдает с ожидаемой");
        return pageManager.getCartPage();
    }

    public CartPage deleteProductFromCart(String code) {
        WebElement productToDelete = getWebElementByCode(code);
        productToDelete.findElement(By.xpath(".//button[@class='menu-control-button remove-button']")).click();
        wait.until(ExpectedConditions.invisibilityOf(productToDelete));
        productCollection.removeProduct(code);
        return pageManager.getCartPage();
    }

    public CartPage increaseProductCount(String code) {
        WebElement productToIncrease = getWebElementByCode(code);
        productToIncrease.findElement(By.xpath(".//button[contains(@class, 'button_plus')]")).click();
        productCollection.addProductToMap(productCollection.getProductByCode(code));
        String expectedValue = String.valueOf(cartBlock.productsInCartCount() + 1);
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class='cart-link-counter__badge']"), expectedValue));
        return pageManager.getCartPage();
    }

    public CartPage restoreLastRemovedProduct() {
        WebElement restoreLastRemovedButton = totalAmountBlock.findElement(By.xpath(".//span[@class='restore-last-removed']"));
        wait.until(ExpectedConditions.elementToBeClickable(restoreLastRemovedButton)).click();
        wait.until(ExpectedConditions.invisibilityOf(restoreLastRemovedButton));
        productCollection.restoreLastRemoved();
        return pageManager.getCartPage();
    }

    private WebElement getWebElementByCode(String code) {
        for (WebElement elem : productList) {
            String currentCode = elem.findElement(By.xpath(".//div[@class='cart-items__product-code']/div")).getText();
            if (currentCode.equals(code)) {
                return elem;
            }
        }
        Assertions.fail("В корзине не найден элемент с кодом " + code);
        return null;
    }

    private int getGuaranteePrice(WebElement element) {
        int price = 0;
        try {
            WebElement guaranteePrice = element.findElement(By.xpath(".//following-sibling::span"));
            price = parsePriceToInt(guaranteePrice.getText());
        } catch (NoSuchElementException ignored) {
        }
        return price;
    }

    private String getCodeFromElement(WebElement element) {
        return element.findElement(By.xpath(".//div[@class='cart-items__product-code']/div")).getText();
    }

}
