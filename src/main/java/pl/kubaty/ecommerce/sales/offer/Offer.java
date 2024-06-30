package pl.kubaty.ecommerce.sales.offer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class Offer {
    private final List<OfferLine> lines;
    private final BigDecimal totalBeforeDiscount;
    private final BigDecimal totalAfterDiscount;
    private int quantity ;

    public Offer(List<OfferLine> lines, HashMap<String, BigDecimal> total) {
        this.lines = lines;
        this.totalBeforeDiscount = total.get("Before Discount");
        this.totalAfterDiscount = total.get("After Discount");
        for (OfferLine line : lines) {
            quantity = quantity+line.getQuantity();
        }
    }

    public BigDecimal getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public BigDecimal getTotalAfterDiscount() {
        return totalAfterDiscount;
    }

    public List<OfferLine> getLines() {
        return lines;
    }

    public int getQuantity(){
        return quantity;
    }

}
