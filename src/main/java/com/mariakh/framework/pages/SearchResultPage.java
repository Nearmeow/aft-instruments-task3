package com.mariakh.framework.pages;

import com.mariakh.framework.pages.block.PaginationBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultPage extends BasePage {

    private PaginationBlock pagesBlock = new PaginationBlock();

    @FindBy(xpath = "//h1[@class='title']")
    private WebElement title;

    @FindBy(xpath = "//div[@data-id='product']")
    private List<WebElement> productsOnThePage;

    public PaginationBlock getPagesBlock() {
        return pagesBlock;
    }

    public SearchResultPage checkOpenSearchResultPage() {
        Assertions.assertTrue(title.getText().contains("Найдено")
                ,"Заголовок отсутствует или не соответствует ожидаемому");
        return pageManager.getSearchResultPage();
    }

    public ProductCardPage findProductByCodeAndClick(String code) {
        WebElement elem = getProductIfOnCurrentPage(code);
        if (elem != null) {
            scrollToPageTop();
            wait.until(ExpectedConditions.elementToBeClickable(elem.findElement(By.xpath("./a")))).click();
            return pageManager.getProductCardPage();
        }
        scrollToPageBottom();
        return pagesBlock.clickNextPage().findProductByCodeAndClick(code);
    }

    private WebElement getProductIfOnCurrentPage(String code) {
        return productsOnThePage.stream().filter(o ->
                o.getAttribute("data-code").equals(code)).findFirst().orElse(null);
    }
}
