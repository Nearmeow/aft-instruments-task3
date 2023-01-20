package com.mariakh.tests.base;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BaseTests {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); //ожидание везде, где вызван findByElement
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10)); //проверяет загружена ли страница, прежде чем искать элемент
        wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofSeconds(1));
        driver.get("https://www.dns-shop.ru/");

    }

    @AfterEach
    public void after() {
        driver.quit();
    }

    protected int parsePriceString(String priceStr) {
        return Integer.parseInt(priceStr.replaceAll("\\D+",""));
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
