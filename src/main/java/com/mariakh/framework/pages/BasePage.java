package com.mariakh.framework.pages;

import com.mariakh.framework.managers.DriverManager;
import com.mariakh.framework.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class BasePage {

    protected DriverManager driverManager = DriverManager.getInstance();
    protected WebDriverWait wait = new WebDriverWait(driverManager.getDriver(), Duration.ofSeconds(10), Duration.ofSeconds(1));
    protected static int itemsInCartCount = 0;

    protected String parseToStringOnlyDigits(String string) {
        return string.replaceAll("\\D+","");
    }

    protected int parseStringToInt(String string) {
        return Integer.parseInt(parseToStringOnlyDigits(string));
    }

    protected int calcTotalAmountInMap() {
        int count = 0;
        for (Map.Entry<String, Product> pair : CartPage.getPositions().entrySet()) {
            count += pair.getValue().getPrice() + pair.getValue().getGuaranteePrice();
        }
        return count;
    }

    protected WebElement getProductFromListByCode(List<WebElement> list, String code) {

        for (WebElement elem : list) {
            String productCode = elem.findElement(By.xpath(".//div[@class='cart-items__product-code']")).getText();
            if (productCode.equals(code)) {
                return elem;
            }
        }
        return null;
    }
}
