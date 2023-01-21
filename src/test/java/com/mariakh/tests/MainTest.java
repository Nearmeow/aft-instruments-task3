package com.mariakh.tests;

import com.mariakh.tests.base.BaseTests;
import org.junit.jupiter.api.Test;

public class MainTest extends BaseTests {

    // некорректно считывает цену, если она со скидкой и старая цена так же указана
    // надо оптимизировать page manager
    @Test
    public void test() {
        pageManager.getStartPage().getSearchBlock().searchProductAndReturnResultList(propManager.getProperty("text.for.search.first"))
                .checkOpenSearchResultPage()
                .findProductByCodeAndClick(propManager.getProperty("product.code.first"))
                .checkOpenPage()
                .saveProductInfoAndUpdate()
                .clickGuaranteeButton(propManager.getProperty("additional.sales.guarantee.tab.number"))
                .selectNotFreeGuarantee()
                .clickBuy()
                .getSearchBlock().searchProductAndReturnProductCard(propManager.getProperty("text.for.search.second"))
                .checkOpenPage()
                .saveProductInfoAndUpdate()
                .clickBuy()
                .checkTotalAmount()
                .getCartBlock().clickOnCart()
                .checkProductPrices()
                .checkGuaranteePrices()
                .checkTotalAmount()
                .deleteProductFromCart(propManager.getProperty("product.code.second"))
                .checkTotalAmount()
                .increaseProductCount(propManager.getProperty("product.code.first"))
                .increaseProductCount(propManager.getProperty("product.code.first"))
                .checkTotalAmount()
                .restoreLastRemovedProduct()
                .checkTotalAmount();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
