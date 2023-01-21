package com.mariakh.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultPage extends BasePage {

    @FindBy(xpath = "//h1[@class='title']")
    private WebElement title;

    @FindBy(xpath = "//div[@data-id='product']")
    private List<WebElement> productsOnThePage;

    @FindBy(xpath = "//li[contains(@class, 'page_active')]")
    private WebElement currentPageButton;

    public SearchResultPage checkOpenSearchResultPage() {
        Assertions.assertTrue(title.getText().contains("Найдено")
                ,"Заголовок отсутствует или не соответствует ожидаемому");
        return pageManager.getSearchResultPage();
    }

    public ProductCardPage findProductByCodeAndClick(String code) {
        WebElement elem = getProductIfOnCurrentPage(code);
        if (elem != null) {
            elem.findElement(By.xpath("./a/span")).click();
            return pageManager.getProductCardPage();
        }
        Assertions.fail("Элемент не найден");
        return pageManager.getProductCardPage();
    }

    private WebElement getProductIfOnCurrentPage(String code) {
        for (WebElement elem : productsOnThePage) {
            if (elem.getAttribute("data-code").equals(code)) {
                return elem;
            }
        }
        return null;
    }

    public SearchResultPage goToNextPage() {
        WebElement nextButton = currentPageButton.findElement(By.xpath(".//following-sibling::li"));
        scrollToElementJs(nextButton).click();
        wait.until(ExpectedConditions.attributeContains(nextButton, "class", "page_active"));
        return pageManager.getSearchResultPage();
    }

}
