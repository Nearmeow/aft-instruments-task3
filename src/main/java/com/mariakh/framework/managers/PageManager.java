package com.mariakh.framework.managers;

import com.mariakh.framework.pages.CartPage;
import com.mariakh.framework.pages.ProductCardPage;
import com.mariakh.framework.pages.SearchResultPage;
import com.mariakh.framework.pages.StartPage;

public class PageManager {

    private static PageManager instance;

    private StartPage startPage;
    private CartPage cartPage;

    private PageManager() {
    }

    public static PageManager getInstance() {
        if (instance == null) {
            instance = new PageManager();
        }
        return instance;
    }

    public StartPage getStartPage() {
        if (startPage == null) {
            startPage = new StartPage();
        }
        return startPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    public ProductCardPage getProductCardPage() {
        return new ProductCardPage();
    }

    public SearchResultPage getSearchResultPage() {
        return new SearchResultPage();
    }

}
