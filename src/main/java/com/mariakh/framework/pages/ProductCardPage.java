package com.mariakh.framework.pages;

import com.mariakh.framework.model.Product;
import com.mariakh.framework.pages.block.CartBlock;
import com.mariakh.framework.pages.block.SearchBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductCardPage extends BasePage {

    private SearchBlock searchBlock = new SearchBlock();
    private CartBlock cartBlock = new CartBlock();

    private Product product = new Product();

/*    @FindBy(xpath = "//div[@class='product-buy__price']")
    private WebElement priceStr;*/

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

    public ProductCardPage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public SearchBlock getSearchBlock() {
        return searchBlock;
    }

    public CartBlock getCartBlock() {
        return cartBlock;
    }

    public void checkOpenPage() {
        wait.until(ExpectedConditions.visibilityOf(productCard));
        wait.until(ExpectedConditions.visibilityOf(productCard.findElement(By.className("product-buy__price"))));
    }

    public void saveProductInfo() {
        product.setCode(parseToStringOnlyDigits(productCode.getText()));
        WebElement priceInCard = productCard.findElement(By.className("product-buy__price"));
        int price = parseStringToInt(priceInCard.getText());
        product.setPrice(price);
    }


    public void clickGuaranteeButton(String tabNumber) {
        String xpath = String.format("./div[%s]", tabNumber);
        additionalSales.findElement(By.xpath(xpath)).click();
    }

    public void selectNotFreeGuarantee() {
        for (WebElement elem : guaranteeList) {
            String guaranteePriceStr = elem.findElement(By.className("product-warranty__price")).getText();
            int guaranteePrice = Integer.parseInt(guaranteePriceStr.replaceAll("\\D+",""));
            if (guaranteePrice != 0) {
                elem.findElement(By.xpath("./span")).click();
                product.setGuaranteePrice(guaranteePrice);
                return;
            }
        }
        Assertions.fail("Гарантий с ненулевой стоимостью в списке нет");
    }

    public void clickBuy() {
        CartPage.addPositionToCart(product.getCode(), product);
        itemsInCartCount++;
        productCard.findElement(By.xpath(".//button[contains(@class, 'buy-btn')]")).click();
        //wait.until(ExpectedConditions.visibilityOf(itemsInCart));

        ExpectedCondition<Boolean> condition = x -> itemsInCart.getText().equals(String.valueOf(itemsInCartCount));
        wait.until(condition);
    }
    private void check(String value) {

    }
}
