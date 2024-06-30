package pl.kubaty.ecommerce.payu;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import pl.kubaty.ecommerce.sales.SalesFacade;
import pl.kubaty.ecommerce.sales.cart.InMemoryCartStorage;
import pl.kubaty.ecommerce.sales.offer.AcceptOfferRequest;
import pl.kubaty.ecommerce.sales.offer.OfferCalculator;
import pl.kubaty.ecommerce.sales.payment.PayUGateway;
import pl.kubaty.ecommerce.sales.productdetails.InMemoryProductDetailsProvider;
import pl.kubaty.ecommerce.sales.productdetails.ProductDetails;
import pl.kubaty.ecommerce.sales.reservation.ReservationDetail;
import pl.kubaty.ecommerce.sales.reservation.ReservationRepository;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


public class PayUGatewayTest {

    private PayUGateway gateway;
    private ReservationRepository reservationRepository;
    private InMemoryProductDetailsProvider productDetails;
    private InMemoryCartStorage cartStorage;


    @BeforeEach
    void setUp() {
        gateway = new PayUGateway(
                new PayU(
                        new RestTemplate(),
                        PayUCredentials.sandbox(
                                "300746",
                                "2ee86a66e5d97e3fadc400c9f19b065d"
                        )
                )
        );
        reservationRepository = new ReservationRepository();
        productDetails = new InMemoryProductDetailsProvider();
        cartStorage = new InMemoryCartStorage();
    }

    @Test
    void facadeWorksWithRealPayUGateway() {
        SalesFacade salesModule = thereIsSales();
        String customerId = thereIsExampleCustomer("Mateusz");
        String productId = ThereIsProduct("X", BigDecimal.valueOf(10000));

        salesModule.addToCart(customerId, productId);
        salesModule.addToCart(customerId, productId);

        var acceptOfferRequest = new AcceptOfferRequest();
        acceptOfferRequest
                .setFirstName("john")
                .setLastName("doe")
                .setEmail("john.doe@example.com");

        ReservationDetail reservationDetails = salesModule.acceptOfferPayU(customerId, acceptOfferRequest);

        assertThat(reservationRepository.reservations).containsKey(reservationDetails.getReservationId());
        assertThat(reservationDetails.getPaymentUrl()).isNotBlank();
    }

    private SalesFacade thereIsSales() {
        return new SalesFacade(
                cartStorage,
                new OfferCalculator(productDetails),
                gateway,
                reservationRepository
        );
    }

    private String thereIsExampleCustomer(String id) {
        return id;
    }

    private String ThereIsProduct(String name, BigDecimal price) {
        String id = UUID.randomUUID().toString();
        productDetails.add(new ProductDetails(id, name, price));

        return id;
    }
}
