package pl.kubaty.ecommerce.sales;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.kubaty.ecommerce.sales.cart.InMemoryCartStorage;
import pl.kubaty.ecommerce.sales.offer.Offer;
import pl.kubaty.ecommerce.sales.offer.OfferCalculator;
import pl.kubaty.ecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetails;
import pl.kubaty.ecommerce.sales.reservation.ReservationRepository;
import pl.kubaty.ecommerce.sales.reservation.SpyPaymentGateway;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.UUID;

public class SalesTest {

    private InMemoryProductDetailsProvider productDetails;

    @BeforeEach
    void setUp() {
        this.productDetails = new InMemoryProductDetailsProvider();
    }
    @Test
    void itShowsOffer(){
        SalesFacade sales = thereIsSAlesFacade();
        String customerId = thereIsExampleCustomer("Mateusz");

        Offer offer = sales.getCurrentOffer(customerId);

        assertEquals(0, offer.getQuantity());
        assertEquals(BigDecimal.ZERO, offer.getTotalAfterDiscount());
    }

    private String thereIsExampleCustomer(String id) {
        return id;
    }

    private SalesFacade thereIsSAlesFacade() {
        return new SalesFacade(
                new InMemoryCartStorage(),
                new OfferCalculator(productDetails),
                new SpyPaymentGateway(),
                new ReservationRepository()
        );
    }

    @Test
    void itAllowsToAddProductToCart(){
        var customerId = thereIsExampleCustomer("Mateusz");
        var product = thereIsProduct("product", BigDecimal.valueOf(10));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerId, product);

        Offer offer = sales.getCurrentOffer(customerId);
        assertEquals(BigDecimal.valueOf(10), offer.getTotalAfterDiscount());
        assertEquals(1, offer.getQuantity());

    }

    @Test
    void itAllowsToAddMultipleProductsToCart(){
        var customerId = thereIsExampleCustomer("Mateusz");
        var productA = thereIsProduct("product a", BigDecimal.valueOf(10));
        var productB = thereIsProduct("product b", BigDecimal.valueOf(20));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerId, productA);
        sales.addToCart(customerId, productB);

        Offer offer = sales.getCurrentOffer(customerId);
        assertEquals(BigDecimal.valueOf(30), offer.getTotalAfterDiscount());
        assertEquals(2, offer.getQuantity());

    }

    @Test
    void itDoesNotShareCustomersCarts(){
        var customerA = thereIsExampleCustomer("Mateusz");
        var customerB = thereIsExampleCustomer("kek");
        var productA = thereIsProduct("product A", BigDecimal.valueOf(10));
        var productB = thereIsProduct("product B", BigDecimal.valueOf(20));

        SalesFacade sales = thereIsSAlesFacade();

        sales.addToCart(customerA, productA);
        sales.addToCart(customerB, productB);

        Offer offerA = sales.getCurrentOffer(customerA);
        assertEquals(BigDecimal.valueOf(10), offerA.getTotalAfterDiscount());

        Offer offerB = sales.getCurrentOffer(customerB);
        assertEquals(BigDecimal.valueOf(20), offerB.getTotalAfterDiscount());

    }

    private String thereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        this.productDetails.add(new ProductDetails(
               id,
               name,
               price
        ));
        return id;
    }

    @Test
    void itAllowsToRemoveProductFromCart(){
    }

    @Test
    void itAllowsToAcceptOffer(){
    }

}
