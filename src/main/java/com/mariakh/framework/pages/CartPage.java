package com.mariakh.framework.pages;

import com.mariakh.framework.model.Product;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPage extends BasePage {

    private static Map<String, Product> positions = new HashMap<>();
    private int totalPrice;

    @FindBy(xpath = "//div[@class='cart-items__product']")
    private List<WebElement> productList;

    @FindBy(xpath = "//div[@class='total-amount__summary-section']")
    private WebElement totalAmountBlock;

    public CartPage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public static Map<String, Product> getPositions() {
        return positions;
    }

    public static void addPositionToCart(String code, Product product) {
        positions.put(code, product);
    }

    public void checkProductPrices() {
        for (WebElement elem : productList) {
            int currentPrice = parseStringToInt(elem.findElement(By.xpath(".//span[@class='price__current']")).getText());
            String currentCode = elem.findElement(By.xpath(".//div[@class='cart-items__product-code']/div")).getText();
            Product currentProduct = positions.get(currentCode);
            Assertions.assertEquals(currentProduct.getPrice(), currentPrice, "Цена продукта с кодом "
                    + currentCode + " не соответствует ожидаемой.");
        }
    }

    public void checkGuaranteePrices() {
        for (WebElement elem : productList) {
            WebElement checkedGuarantee = elem.findElement(By.xpath(".//div[contains(@class, 'checked')]"));
            //WebElement guaranteePrice = checkedGuarantee.findElement(By.xpath(".//following-sibling::span"));

            //поаторяется код иди поправь
            String currentCode = elem.findElement(By.xpath(".//div[@class='cart-items__product-code']/div")).getText();
            int price = getGuaranteePrice(checkedGuarantee);
            Assertions.assertEquals(positions.get(currentCode).getGuaranteePrice(), price);

        }
    }

    public void checkTotalAmount() {
        WebElement currentPriceElement = totalAmountBlock.findElement(By.xpath(".//span[@class='price__current']"));
        int currentTotalPrice = parseStringToInt(currentPriceElement.getText());
        System.out.println(currentTotalPrice);
        Assertions.assertEquals(calcTotalAmountInMap(), currentTotalPrice, "Общая стоимость всех товаров не совпдает с ожидаемой");
    }

    public void deleteProductFromCart(String code) {
        for (WebElement elem : productList) {
            String currentCode = elem.findElement(By.xpath(".//div[@class='cart-items__product-code']/div")).getText();
            if (currentCode.equals(code)) {
                elem.findElement(By.xpath(".//button[@class='menu-control-button remove-button']")).click();
                wait.until(ExpectedConditions.invisibilityOf(elem));
                positions.remove(code);
                itemsInCartCount--;
                return;
            }
        }
        Assertions.fail("В корзине не найден элемент с кодом " + code);
    }

    private int getGuaranteePrice(WebElement element) {
        int price = 0;
        try {
            WebElement guaranteePrice = element.findElement(By.xpath(".//following-sibling::span"));
            price = parseStringToInt(guaranteePrice.getText());
        } catch (NoSuchElementException ignored) {
        }
        return price;
    }


}
