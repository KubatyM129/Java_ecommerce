package pl.kubaty.ecommerce.sales.offering;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kubaty.ecommerce.sales.offer.Offer;
import pl.kubaty.ecommerce.sales.offer.OfferCalculator;
import pl.kubaty.ecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetails;
import pl.kubaty.ecommerce.sales.cart.CartLine;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class OfferCalculatorTest {

    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setUp() {
        this.productDetails = new InMemoryProductDetailsProvider();
    }
    @Test
    void zeroOfferForEmptyItems() {
        OfferCalculator offerCalculator = new OfferCalculator(productDetails);

        Offer offer = offerCalculator.calculateOffer(Collections.emptyList());

        assertThat(offer.getTotalAfterDiscount()).isEqualTo(BigDecimal.ZERO);
    }

    @Test
    public void itCreateOfferBasedOnCartItems() {
        String product1 = thereIsProduct("Lego set 1", BigDecimal.valueOf(10.10));
        String product2 = thereIsProduct("Lego set 2", BigDecimal.valueOf(20.10));
        List<CartLine> cartItems = Arrays.asList(
                new CartLine(product1, 2),
                new CartLine(product2, 1)
        );

        OfferCalculator offerCalculator = thereIsOfferCalculator();

        Offer offer = offerCalculator.calculateOffer(cartItems);

        assertThat(offer.getTotalAfterDiscount())
                .isEqualTo(BigDecimal.valueOf(40.3));
    }

    private OfferCalculator thereIsOfferCalculator() {
        return new OfferCalculator(productDetails);
    }

    private String thereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        this.productDetails.add(new ProductDetails(id, name, price));
        return id;
    }


}
