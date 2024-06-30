package pl.kubaty.ecommerce.sales.offer;


import java.math.BigDecimal;
import pl.kubaty.ecommerce.sales.cart.CartLine;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetails;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetailsProvider;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OfferCalculator {
    ProductDetailsProvider productDetailsProvider;


    public OfferCalculator(ProductDetailsProvider productDetailsProvider) {
        this.productDetailsProvider = productDetailsProvider;
    }

    public Offer calculateOffer(List<CartLine> cartLines) {
        List<OfferLine> offerLines = new ArrayList<>();

        for(CartLine cartLine : cartLines) {
            offerLines.add(toOfferLine(cartLine));
        }

        return new Offer(offerLines, calculateOfferTotal(offerLines));
    }

    public OfferLine toOfferLine(CartLine cartLine) {
        ProductDetails productDetails = productDetailsProvider.load(cartLine.getProductId()).get();

        BigDecimal lineTotal = productDetails.getPrice().multiply(BigDecimal.valueOf(cartLine.getQuantity()));

        return new OfferLine(
                cartLine.getProductId(),
                productDetails.getName(),
                productDetails.getPrice(),
                cartLine.getQuantity(),
                lineTotal);
    }

    public HashMap<String, BigDecimal>  calculateOfferTotal(List<OfferLine> offerLines) {

        HashMap<String, BigDecimal> offerTotal = new HashMap<>();

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal afterDiscount;

        for(OfferLine line : offerLines) {
            total = total.add(line.getTotal());
        }
        offerTotal.put("Before Discount", total);

        if(total.compareTo(BigDecimal.valueOf(300)) >= 0) {
            afterDiscount = total.multiply(BigDecimal.valueOf(0.8)).setScale(2, RoundingMode.HALF_DOWN);
            offerTotal.put("After Discount", afterDiscount);
        } else if (total.compareTo(BigDecimal.valueOf(100)) >= 0 ){
            afterDiscount = total.multiply(BigDecimal.valueOf(0.9)).setScale(2, RoundingMode.HALF_DOWN);
            offerTotal.put("After Discount", afterDiscount);
        } else {
            offerTotal.put("After Discount", total);
        }
        return offerTotal;

    }
}