package com.mariakh.framework.pages.block;

import com.mariakh.framework.pages.BasePage;
import com.mariakh.framework.pages.ProductCardPage;
import com.mariakh.framework.pages.SearchResultPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchBlock extends BasePage {

    @FindBy(xpath = "//input[@class='presearch__input']")
    private WebElement searchField;

    @FindBy(xpath = "//span[@class='presearch__icon-search']")
    private WebElement loupButton;

    public SearchResultPage searchProductAndReturnResultList(String product) {
        searchField.sendKeys(product);
        loupButton.click();
        return pageManager.getSearchResultPage();
    }

    public ProductCardPage searchProductAndReturnProductCard(String product) {
        searchField.sendKeys(product);
        loupButton.click();
        return pageManager.getProductCardPage();
    }

}
