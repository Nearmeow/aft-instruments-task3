package com.mariakh.tests;

import com.mariakh.framework.pages.SearchResultPage;
import com.mariakh.framework.pages.StartPage;
import com.mariakh.tests.base.BaseTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class MainTest extends BaseTests {

    StartPage startPage = new StartPage();
    SearchResultPage searchResultPage = new SearchResultPage();

    @Test
    public void test() {

        startPage.searchProduct("iphone");
        searchResultPage.checkOpenSearchResultPage();
        searchResultPage.findProductByCodeAndClick("5072935");



        //Ввели в строку поиска айфон и нажали на лупу
/*        WebElement searchField = driver.findElement(By.xpath("//input[@class='presearch__input']"));
        WebElement loupButton = driver.findElement(By.xpath("//span[@class='presearch__icon-search']"));
        searchField.sendKeys("iphone");
        loupButton.click();*/


        // Нашли айфон по артикулу и перешли в карточку товара
/*        WebElement elementByDataCode = driver.findElement(By.xpath("//div[@data-code=5072935]"));
        WebElement elementLink = elementByDataCode.findElement(By.xpath("./a/span"));
        elementLink.click();*/


        //WebElement productCode = driver.findElement(By.xpath("//div[@class='catalog-product__code']"));

        //Нашли цену в карточке товара и сохранили ее в переменную
        WebElement productPrice = driver.findElement(By.xpath("//div[@class='product-buy__price']"));
        wait.until(ExpectedConditions.visibilityOf(productPrice));
        String productPriceStr = productPrice.getText();
        int price = Integer.parseInt(productPriceStr.replaceAll("\\D+",""));
        System.out.println(productPriceStr);
        System.out.println(price);

        //номер дива возьми из пропертей
        WebElement guaranteeButton = driver.findElement(By.xpath("//div[@class='additional-sales-tabs__titles-wrap']/div[2]"));

        guaranteeButton.click();

        List<WebElement> guaranteeList = driver.findElements(By.xpath("//label[contains(@class, 'product-warranty__item')]"));

        //удалим из списка гарантий ту, у которой цена нулевая
        guaranteeList.removeIf(element -> (parsePriceString(element.findElement(By.className("product-warranty__price")).getText())==0));
        for (WebElement elem : guaranteeList) {
            WebElement priceE = elem.findElement(By.className("product-warranty__price"));
            System.out.println(priceE.getText());
        }

        // берем случайную не бесплатную гарантию из списка
        Random random = new Random();
        WebElement selectedGuarantee = guaranteeList.get(random.nextInt(guaranteeList.size()));

        // сохраняем ее цену в переменную
        int guaranteePrice = parsePriceString(selectedGuarantee.findElement(By.className("product-warranty__price")).getText());

        //кликнули на саму гарантию, чтобы чекбокс отметился
        selectedGuarantee.findElement(By.xpath("./span")).click();


        //WebElement buyButton = driver.findElement(By.xpath("//button[contains(@class, 'buy-btn')]"));

        // карточку товара находим
        WebElement productCard = driver.findElement(By.xpath("//div[@class='product-card-top__buy']"));

        // находим кнопку покупки в основной карточке товара
        WebElement buyButton = driver.findElement(By.xpath("//div[@class='product-card-top__buy']/div/button[2]"));
        buyButton.click();

        //вводим в строку поиска новый товар и жмем на лупу
        WebElement search = driver.findElement(By.xpath("//input[@class='presearch__input']"));
        WebElement lupa = driver.findElement(By.xpath("//span[@class='presearch__icon-search']"));

        //wait.until(ExpectedConditions.elementToBeClickable(searchField));
        search.sendKeys("Apple AirPods Pro 2");
        lupa.click();

        // нашли цену наушников и сохранили ее в переменную
        WebElement airpodsPrice = driver.findElement(By.xpath("//div[@class='product-buy__price']"));
        wait.until(ExpectedConditions.visibilityOf(airpodsPrice));
        String airpodsPriceStr = airpodsPrice.getText();
        int airpodsPriceInt = parsePriceString(airpodsPriceStr);

        System.out.println(airpodsPriceInt);

        //кликнули купиц
        WebElement productCardPods = driver.findElement(By.xpath("//div[@class='product-card-top__buy']"));
        productCardPods.findElement(By.xpath("./div/button[2]")).click();



        // ожидалка пока в корзине не отобразится нужное количество товаров
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class='cart-link-counter__badge']"), "2"));
        WebElement priceCart = driver.findElement(By.xpath("//span[@class='buttons__link-price cart-link-counter__price']"));

        int allPrice = parsePriceString(priceCart.getText());

        Assertions.assertEquals(allPrice, price+guaranteePrice+airpodsPriceInt);

        // кликнули на икнонку корзины для перехода в нее
        WebElement cartButton = driver.findElement(By.xpath("//a[@data-commerce-target='CART']"));
        cartButton.click();

        // список товаров в корзинке
        List<WebElement> cartItemProducts = driver.findElements(By.xpath("//div[@class='cart-items__product']"));

        // ищем карточку товара с нужным артикулом
        WebElement iphoneInCart = getProductFromListByCode(cartItemProducts, "5072935");

        // находим блок с гарантией  в карточке выбранного товара по отмеченному чекбоксу
        WebElement guaranteeIphoneInCart = iphoneInCart.findElement(By.xpath(".//div[contains(@class, 'checked')]"));

        // ищем цену гарантии в карточке
        String guaranteePriceInCart = guaranteeIphoneInCart.findElement(By.xpath("./../span")).getText();

        Assertions.assertEquals(guaranteePrice, parsePriceString(guaranteePriceInCart));

        // Ищем текущие цены из карточек товаров в корзине
        String currentPrice1 = cartItemProducts.get(0).findElement(By.xpath(".//span[@class='price__current']")).getText();
        String currentPrice2 = cartItemProducts.get(1).findElement(By.xpath(".//span[@class='price__current']")).getText();
        WebElement totalAmountBlock = driver.findElement(By.xpath("//div[@class='total-amount__summary-section']"));
        String totalAmountPrice = totalAmountBlock.findElement(By.xpath(".//span[@class='price__current']")).getText();

        Assertions.assertEquals(price, parsePriceString(currentPrice1));
        Assertions.assertEquals(airpodsPriceInt, parsePriceString(currentPrice2));
        Assertions.assertEquals(allPrice, parsePriceString(totalAmountPrice));

        // ищем кнопку удалить отталкиваясь от конкретного элемента корзины и жмем на кнопку
        WebElement delAirPodsButton = cartItemProducts.get(1).findElement(By.xpath(".//button[@class='menu-control-button remove-button']"));
        delAirPodsButton.click();

        // ждем пока элемент не пропадет с экрана
        wait.until(ExpectedConditions.invisibilityOf(cartItemProducts.get(1)));

        // по новой собираем список элементов корзины и проверяем, что его длина уменьшилась
        List<WebElement> cartItemProducts2 = driver.findElements(By.xpath("//div[@class='cart-items__product']"));
        Assertions.assertEquals(1, cartItemProducts2.size());

        // ищем новую общую стоимость, проверяем, что она уменьшилась на стоимсоть наушников
        String totalAmountPrice2 = totalAmountBlock.findElement(By.xpath(".//span[@class='price__current']")).getText();
        Assertions.assertEquals(parsePriceString(totalAmountPrice2), parsePriceString(totalAmountPrice) - airpodsPriceInt);


        // ищем плюсик в карточке конкретного товара, кликаем
        WebElement plusIphoneButton = cartItemProducts.get(0).findElement(By.xpath(".//button[@class='count-buttons__button count-buttons__button_plus']"));
        plusIphoneButton.click();
        // ждем пока количество товаров в корзине поменяется
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class='cart-link-counter__badge']"), "2"));
        plusIphoneButton.click();
        wait.until(ExpectedConditions.textToBe(By.xpath("//span[@class='cart-link-counter__badge']"), "3"));

        // ищем новую общую стоимость
        WebElement newTotalPrice = totalAmountBlock.findElement(By.xpath(".//span[@class='price__current']"));
        Assertions.assertEquals(parsePriceString(newTotalPrice.getText()), (price + guaranteePrice) * 3);
        System.out.println(newTotalPrice.getText());

        // ищем блок общей стоимости корзинки
        WebElement cartTabTotalAmount = driver.findElement(By.xpath("//div[@class='cart-tab-total-amount']"));

        // ищем в найденном блоке кнопку 'вернуть удаленный товар'
        WebElement restoreLastRemovedButton = cartTabTotalAmount.findElement(By.xpath(".//span[@class='restore-last-removed']"));

        // ждем пока кнопка будет кликабельна и кликаем на нее
        wait.until(ExpectedConditions.elementToBeClickable(restoreLastRemovedButton)).click();


        // напиши там как-нибудь еще, что айрподсы вернулись в нашу корзинку

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
