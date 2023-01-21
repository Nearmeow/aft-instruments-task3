package com.mariakh.framework.managers;

import com.mariakh.framework.pages.CartPage;
import com.mariakh.framework.pages.ProductCardPage;
import com.mariakh.framework.pages.SearchResultPage;
import com.mariakh.framework.pages.StartPage;

public class PageManager {

    private static PageManager instance;
    private StartPage startPage;
    private CartPage cartPage;
    private SearchResultPage searchResultPage;
    private ProductCardPage productCardPage;

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

    public SearchResultPage getSearchResultPage() {
        if (searchResultPage == null) {
            searchResultPage = new SearchResultPage();
        }
        return searchResultPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) {
            cartPage = new CartPage();
        }
        return cartPage;
    }

    public ProductCardPage getProductCardPage() {
        if (productCardPage == null) {
            productCardPage = new ProductCardPage();
        }
        return productCardPage;
    }
}
