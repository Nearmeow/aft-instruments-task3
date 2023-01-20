package com.mariakh.framework.model;

public class Product {

    private String code;
    private int price;
    private int guaranteePrice;

    public Product(String code, int price, int guaranteePrice) {
        this.code = code;
        this.price = price;
        this.guaranteePrice = guaranteePrice;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getGuaranteePrice() {
        return guaranteePrice;
    }

    public void setGuaranteePrice(int guaranteePrice) {
        this.guaranteePrice = guaranteePrice;
    }
}
