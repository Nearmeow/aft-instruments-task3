package com.mariakh.framework.managers;

import com.mariakh.framework.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {

    private static ProductManager instance;
    private Map<String, Product> productByCodeMap;
    private Product lastRemovedProduct;

    private ProductManager() {
        productByCodeMap = new HashMap<>();
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public void addProductToMap(Product product) {
        product.incCount();
        productByCodeMap.put(product.getCode(), product);
    }

    public void removeProduct(Product product) {
        lastRemovedProduct = product;
        productByCodeMap.remove(product.getCode());
    }

    public void removeProduct(String code) {
        lastRemovedProduct = getProductByCode(code);
        productByCodeMap.remove(code);
    }

    public void restoreLastRemoved() {
        productByCodeMap.put(lastRemovedProduct.getCode(), lastRemovedProduct);
    }

    public Product getProductByCode(String code) {
        return productByCodeMap.get(code);
    }

    public Integer getProductsCount() {
        return productByCodeMap.values().stream().mapToInt(Product::getCount).sum();
    }

    public Integer getTotalAmountOfSavedProducts() {
        return productByCodeMap.values().stream().mapToInt(value ->
                (value.getPrice() + value.getGuaranteePrice()) * value.getCount()).sum();
    }
}
