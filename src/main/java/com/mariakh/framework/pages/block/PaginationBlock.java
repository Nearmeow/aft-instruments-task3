package com.mariakh.framework.pages.block;

import com.mariakh.framework.pages.BasePage;
import com.mariakh.framework.pages.SearchResultPage;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaginationBlock extends BasePage {

    @FindBy(xpath = "//ul[@class='pagination-widget__pages']")
    private WebElement pagesWidget;

    @FindBy(xpath = "//li[contains(@class, 'page_active')]")
    private WebElement currentPageNumberButton;

    @FindBy(xpath = "//li[@class='pagination-widget__page'][last()]")
    private WebElement lastPage;
    private int currentPageNum = 1;
    private int lastPageNum;

    {
        wait.until(ExpectedConditions.visibilityOf(lastPage));
        lastPageNum = Integer.parseInt(lastPage.getAttribute("data-page-number"));
    }

    public SearchResultPage clickNextPage() {
        currentPageNum++;
        checkPageNumber();
        String xpath = String.format(".//a[text()=%s]", currentPageNum);
        pagesWidget.findElement(By.xpath(xpath)).click();
        waitTextToBe(currentPageNumberButton, String.valueOf(currentPageNum));
        return pageManager.getSearchResultPage();
    }

    private void checkPageNumber() {
        if (currentPageNum > lastPageNum) {
            Assertions.fail(String.format("Вся выдача просмотрена (%d страниц), но элемент не был найден.", currentPageNum - 1));
        }
    }
}
