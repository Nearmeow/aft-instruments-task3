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

    private Product product = new Product();

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

    public ProductCardPage saveProductInfo() {
        product.setCode(parseToStringOnlyDigits(productCode.getText()));
        WebElement priceInCard = productCard.findElement(By.className("product-buy__price"));
        int price = parseStringToInt(priceInCard.getText());
        product.setPrice(price);
        return this;
    }

    public ProductCardPage clickGuaranteeButton(String tabNumber) {
        String xpath = String.format("./div[%s]", tabNumber);
        additionalSales.findElement(By.xpath(xpath)).click();
        return this;
    }

    public ProductCardPage selectNotFreeGuarantee() {
        for (WebElement elem : guaranteeList) {
            String guaranteePriceStr = elem.findElement(By.className("product-warranty__price")).getText();
            int guaranteePrice = Integer.parseInt(guaranteePriceStr.replaceAll("\\D+",""));
            if (guaranteePrice != 0) {
                elem.findElement(By.xpath("./span")).click();
                product.setGuaranteePrice(guaranteePrice);
                return this;
            }
        }
        Assertions.fail("Гарантий с ненулевой стоимостью в списке нет");
        return this;
    }

    public ProductCardPage clickBuy() {
        productCard.findElement(By.xpath(".//button[contains(@class, 'buy-btn')]")).click();
        productCollection.addProductToMap(product);
        ExpectedCondition<Boolean> condition = x -> itemsInCart.getText().equals(String.valueOf(productCollection.getProductsCount()));
        wait.until(condition);
        return this;
    }

    public ProductCardPage checkTotalAmount() {
        cartBlock.checkTotalAmount();
        return this;
    }

}
