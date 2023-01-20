package com.mariakh.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SearchResultPage extends BasePage {

    @FindBy(xpath = "//h1[@class='title']")
    private WebElement title;

    @FindBy(xpath = "//div[@data-id='product']")
    List<WebElement> productsOnThePage;

    public SearchResultPage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public void checkOpenSearchResultPage() {
        Assertions.assertTrue(title.getText().contains("Найдено")
                ,"Заголовок отсутствует или не соответствует ожидаемому");
    }

    public void findProductByCodeAndClick(String code) {
        for (WebElement elem : productsOnThePage) {
            if (elem.getAttribute("data-code").equals(code)) {
                elem.findElement(By.xpath("./a/span")).click();
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }
}
