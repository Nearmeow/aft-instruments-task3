package com.mariakh.tests;

import com.mariakh.tests.base.BaseTests;
import org.junit.jupiter.api.Test;

public class MainTest extends BaseTests {

    @Test
    public void test() {
        pageManager.getStartPage().getSearchBlock().searchProductAndReturnResultList(propManager.getTestData("text.for.search.first"))
                .checkOpenSearchResultPage()
                .findProductByCodeAndClick(propManager.getTestData("product.code.first"))
                .checkOpenPage()
                .saveProductInfoAndUpdate()
                .selectNotFreeGuarantee()
                .clickBuy()
                .getSearchBlock().searchProductAndReturnProductCard(propManager.getTestData("text.for.search.second"))
                .checkOpenPage()
                .saveProductInfoAndUpdate()
                .clickBuy()
                .checkTotalAmount()
                .getCartBlock().clickOnCart()
                .checkProductPrices()
                .checkGuaranteePrices()
                .checkTotalAmount()
                .deleteProductFromCart(propManager.getTestData("product.code.second"))
                .checkTotalAmount()
                .increaseProductCount(propManager.getTestData("product.code.first"))
                .increaseProductCount(propManager.getTestData("product.code.first"))
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
