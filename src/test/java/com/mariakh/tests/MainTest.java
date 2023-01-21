package com.mariakh.tests;

import com.mariakh.tests.base.BaseTests;
import org.junit.jupiter.api.Test;

public class MainTest extends BaseTests {

    @Test
    public void test() {
        pageManager.getStartPage().getSearchBlock().searchProductAndReturnResultList(propManager.getProperty("text.for.search.first"))
                .checkOpenSearchResultPage()
                .findProductByCodeAndClick(propManager.getProperty("product.code.first"))
                .checkOpenPage()
                .saveProductInfo()
                .clickGuaranteeButton(propManager.getProperty("additional.sales.guarantee.tab.number"))
                .selectNotFreeGuarantee()
                .clickBuy()
                .getSearchBlock().searchProductAndReturnProductCard(propManager.getProperty("text.for.search.second"))
                .checkOpenPage()
                .saveProductInfo()
                .clickBuy()
                .checkTotalAmount()
                .getCartBlock().clickOnCart()
                .checkProductPrices()
                .checkGuaranteePrices()
                .checkTotalAmount()
                .deleteProductFromCart(propManager.getProperty("product.code.second"))
                .checkTotalAmount()
                .increaseProductCountByCode(propManager.getProperty("product.code.first"))
                .increaseProductCountByCode(propManager.getProperty("product.code.first"))
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
