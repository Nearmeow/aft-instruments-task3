package com.mariakh.framework.pages;

import com.mariakh.framework.model.Product;
import com.mariakh.framework.pages.block.CartBlock;
import com.mariakh.framework.pages.block.SearchBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class ProductCardPage extends BasePage {

    private SearchBlock searchBlock = new SearchBlock();
    private CartBlock cartBlock = new CartBlock();

    private Product currentProduct;

    @FindBy(xpath = "//div[@class='additional-sales-tabs__titles-wrap']/div")
    private List<WebElement> additionalSalesTabs;

    @FindBy(xpath = "//label[contains(@class, 'product-warranty__item')]")
    private List<WebElement> guaranteeList;

    @FindBy(xpath = "//div[@class='product-warranty__items']")
    private WebElement guaranteeBlock;

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

    public void clickGuaranteeBlock() {
        for (int i = 1; i <= additionalSalesTabs.size(); i++) {
            WebElement currentTab = additionalSalesTabs.get(i);
            currentTab.click();
            wait.until(ExpectedConditions.attributeContains(currentTab, "class", "title_active"));
            if (guaranteeBlock.isDisplayed()) {
                return;
            }
/*            Assertions.assertTrue(currentTab.getText().contains("Гарантия")
                    , "Заголовок кнопки не содержит слова 'Гарантия'");*/
        }
        Assertions.fail("Блок с гарантией не найден.");
    }

    public ProductCardPage selectNotFreeGuarantee() {
        clickGuaranteeBlock();
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
        waitTextToBe(itemsInCart, String.valueOf(productCollection.getProductsCount()));
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
