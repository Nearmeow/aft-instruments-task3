package com.mariakh.framework.managers;

import com.mariakh.framework.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {

    private static ProductManager instance;
    private Map<String, Product> productMap;
    private Product lastRemovedProduct;

    private ProductManager() {
        productMap = new HashMap<>();
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public void addProductToMap(Product product) {
        product.incCount();
        productMap.put(product.getCode(), product);
    }

    public void removeProduct(Product product) {
        lastRemovedProduct = product;
        productMap.remove(product.getCode());
    }

    public void removeProduct(String code) {
        lastRemovedProduct = getProductByCode(code);
        productMap.remove(code);
    }

    public void restoreLastRemoved() {
        productMap.put(lastRemovedProduct.getCode(), lastRemovedProduct);
    }

    public Product getProductByCode(String code) {
        return productMap.get(code);
    }

    public Integer getProductsCount() {
        return productMap.values().stream().mapToInt(Product::getCount).sum();
    }

    public Integer getTotalAmountOfSavedProducts() {
        return productMap.values().stream().mapToInt(value ->
                (value.getPrice() + value.getGuaranteePrice()) * value.getCount()).sum();
    }
}
