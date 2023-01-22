package com.mariakh.framework.pages;

import com.mariakh.framework.model.Product;
import com.mariakh.framework.pages.block.CartBlock;
import com.mariakh.framework.pages.block.SearchBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductCardPage extends BasePage {

    private SearchBlock searchBlock = new SearchBlock();
    private CartBlock cartBlock = new CartBlock();

    private Product currentProduct;

    @FindBy(xpath = "//div[@class='additional-sales-tabs__titles-wrap']")
    private WebElement additionalSales;

    @FindBy(xpath = "//label[contains(@class, 'product-warranty__item')]")
    private List<WebElement> guaranteeList;

    @FindBy(xpath = "//div[@class='product-card-top__buy']")
    private WebElement productCard;

    @FindBy(xpath = "//div[@class='product-card-top__code']")
    private WebElement productCode;

    @FindBy(xpath = "//span[@class='cart-link-counter__badge']")
    private WebElement itemsInCart;

    public SearchBlock getSearchBlock() {
        return searchBlock;
    }

    public CartBlock getCartBlock() {
        return cartBlock;
    }

    public ProductCardPage checkOpenPage() {
        wait.until(ExpectedConditions.visibilityOf(productCard));
        wait.until(ExpectedConditions.visibilityOf(productCard.findElement(By.className("product-buy__price"))));
        return this;
    }

    public ProductCardPage clickGuaranteeButton(String tabNumber) {
        String xpath = String.format("./div[%s]", tabNumber);
        WebElement guaranteeButton = additionalSales.findElement(By.xpath(xpath));
        guaranteeButton.click();
        Assertions.assertTrue(guaranteeButton.getText().contains("Гарантия")
                , "Заголовок кнопки не содержит слова 'Гарантия'");
        return this;
    }

    public ProductCardPage selectNotFreeGuarantee() {
        for (WebElement elem : guaranteeList) {
            String guaranteePriceStr = elem.findElement(By.className("product-warranty__price")).getText();
            int guaranteePrice = parsePriceToInt(guaranteePriceStr);
            if (guaranteePrice != 0) {
                elem.findElement(By.xpath("./span")).click();
                currentProduct.setGuaranteePrice(guaranteePrice);
                return this;
            }
        }
        Assertions.fail("Гарантий с ненулевой стоимостью в списке нет");
        return this;
    }

    public ProductCardPage clickBuy() {
        productCard.findElement(By.xpath(".//button[contains(@class, 'buy-btn')]")).click();
        productCollection.addProductToMap(currentProduct);
        ExpectedCondition<Boolean> condition = x -> itemsInCart.getText().equals(String.valueOf(productCollection.getProductsCount()));
        wait.until(condition);
        return this;
    }

    public ProductCardPage checkTotalAmount() {
        cartBlock.checkTotalAmount();
        return this;
    }

        public ProductCardPage saveProductInfoAndUpdate() {
        currentProduct = new Product();
        currentProduct.setCode(cleanString(productCode.getText()));
        WebElement priceBlock = productCard.findElement(By.xpath(".//div[contains(@class, 'product-buy__price-wrap_interactive')]"));
        String priceInCard = priceBlock.findElement(By.xpath("./div[1]")).getText();
        int price = parsePriceToInt(priceInCard);
        currentProduct.setPrice(price);
        return this;
    }

}
