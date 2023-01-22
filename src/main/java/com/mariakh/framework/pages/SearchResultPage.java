package com.mariakh.framework.pages;

import com.mariakh.framework.pages.block.PagesBlock;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SearchResultPage extends BasePage {

    private PagesBlock pagesBlock = new PagesBlock();

    @FindBy(xpath = "//h1[@class='title']")
    private WebElement title;

    @FindBy(xpath = "//div[@data-id='product']")
    private List<WebElement> productsOnThePage;

/*    @FindBy(xpath = "//li[contains(@class, 'page_active')]")
    private WebElement currentPageNumberButton;*/

/*    @FindBy(xpath = "//ul[@class='pagination-widget__pages']")
    private WebElement pagesWidget;*/

    public PagesBlock getPagesBlock() {
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
        //return goToNextPage().findProductByCodeAndClick(code);
        //Assertions.fail("Элемент не найден");
        //return pageManager.getProductCardPage();
    }

    private WebElement getProductIfOnCurrentPage(String code) {
        for (WebElement elem : productsOnThePage) {
            if (elem.getAttribute("data-code").equals(code)) {
                return elem;
            }
        }
        return null;
    }

    /*private SearchResultPage goToNextPage() {
        int currentPageNumber = Integer.parseInt(currentPageNumberButton.getText());
        String xpath = String.format(".//li[@data-page-number=%s]", currentPageNumber + 1);
        pagesWidget.findElement(By.xpath(xpath)).click();
        //WebElement nextButton = currentPageNumberButton.findElement(By.xpath(".//following-sibling::li"));
        //currentPageNumberButton.findElement(By.xpath(".//following-sibling::li")).click();
        //scrollToElementJs(nextButton).click();
        //wait.until(ExpectedConditions.attributeContains(nextButton, "class", "page_active"));
        //waitForDomComplete();
        //wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

*//*        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*//*

        return pageManager.getSearchResultPage();
    }*/



}
