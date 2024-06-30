package pl.kubaty.ecommerce.sales.cart;

public class CartLine {
    String productId;
    int quantity;

    public CartLine(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
