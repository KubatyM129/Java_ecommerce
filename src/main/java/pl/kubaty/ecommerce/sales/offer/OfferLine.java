package pl.kubaty.ecommerce.sales.offer;

import java.math.BigDecimal;

public class OfferLine {
    private final String productId;
    private final String name;
    private final BigDecimal price;
    private final int quantity;
    private final BigDecimal total;

    public OfferLine(String productId, String name, BigDecimal price, int quantity, BigDecimal total) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
