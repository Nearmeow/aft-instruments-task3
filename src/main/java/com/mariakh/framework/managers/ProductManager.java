package com.mariakh.framework.managers;

import com.mariakh.framework.model.Product;

import java.util.HashMap;
import java.util.Map;

public class ProductManager {

    private static ProductManager instance;
    private Map<Product, Integer> productCountMap;
    private Map<String, Product> productByCodeMap;

    private Product lastRemovedProduct;
    private Integer lastRemovedProductCount;

    private ProductManager() {
        productCountMap = new HashMap<>();
        productByCodeMap = new HashMap<>();
    }

    public static ProductManager getInstance() {
        if (instance == null) {
            instance = new ProductManager();
        }
        return instance;
    }

    public void addProductToMap(Product product) {
        productCountMap.merge(product, 1, Integer::sum);
        productByCodeMap.put(product.getCode(), product);
    }

    public void removeProduct(Product product) {
        lastRemovedProduct = product;
        lastRemovedProductCount = productCountMap.get(product);

        productCountMap.remove(product);
        productByCodeMap.remove(product.getCode());
    }

    public void removeProduct(String code) {
        lastRemovedProduct = getProductByCode(code);
        lastRemovedProductCount = productCountMap.get(lastRemovedProduct);

        productCountMap.remove(getProductByCode(code));
        productByCodeMap.remove(code);
    }

    public void restoreLastRemoved() {
        productCountMap.put(lastRemovedProduct, lastRemovedProductCount);
        productByCodeMap.put(lastRemovedProduct.getCode(), lastRemovedProduct);
    }

    public Product getProductByCode(String code) {
        return productByCodeMap.get(code);
    }

    public Integer getProductsCount() {
        return productCountMap.values().stream().mapToInt(Integer::intValue).sum();
    }

    public Integer getTotalAmountOfSavedProducts() {
        int totalAmount = 0;
        for (Map.Entry<Product, Integer> pair : productCountMap.entrySet()) {
            totalAmount += (pair.getKey().getPrice() + pair.getKey().getGuaranteePrice()) * pair.getValue();
        }
        return totalAmount;
    }
}
