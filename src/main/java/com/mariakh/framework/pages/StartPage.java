package com.mariakh.framework.pages;

import com.mariakh.framework.pages.block.SearchBlock;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class StartPage extends BasePage {

    private SearchBlock searchBlock = new SearchBlock();

    public StartPage() {
        PageFactory.initElements(driverManager.getDriver(), this);
    }

    public SearchBlock getSearchBlock() {
        return searchBlock;
    }

}
