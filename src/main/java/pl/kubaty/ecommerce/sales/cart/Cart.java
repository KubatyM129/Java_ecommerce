package pl.kubaty.ecommerce.sales.cart;

import java.util.HashMap;
import java.util.List;

public class Cart {

    HashMap<String, Integer> productsQty;

    public Cart() {
        this.productsQty = new HashMap<>();
    }

    public static Cart empty() {
        return new Cart();
    }

    public void addProduct(String productId) {
        if (!isInCart(productId)) {
            putIntoCart(productId);
        } else {
            increaseQuantity(productId);
        }
    }

    private void increaseQuantity(String productId) {
        productsQty.put(productId, productsQty.get(productId) + 1);
    }

    private void putIntoCart(String productId) {
        productsQty.put(productId, 1);
    }

    private boolean isInCart(String productId) {
        return productsQty.containsKey(productId);
    }

    public boolean isEmpty() {
        return productsQty.isEmpty();
    }

    public int getProductsCount() {
        return productsQty.values().size();
    }

    public List<CartLine> getLines() {
        return productsQty
                .entrySet()
                .stream()
                .map(es -> new CartLine(es.getKey(), es.getValue()))
                .toList();
    }
}
