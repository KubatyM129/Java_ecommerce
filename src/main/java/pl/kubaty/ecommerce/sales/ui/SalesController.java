package pl.kubaty.ecommerce.sales.ui;

import org.springframework.web.bind.annotation.*;
import pl.kubaty.ecommerce.sales.SalesFacade;
import pl.kubaty.ecommerce.sales.offer.AcceptOfferRequest;
import pl.kubaty.ecommerce.sales.offer.Offer;
import pl.kubaty.ecommerce.sales.reservation.ReservationDetail;

@RestController
public class SalesController {
    SalesFacade sales;

    public SalesController(SalesFacade sales) {
        this.sales = sales;
    }

    @GetMapping("/api/current-offer")
    Offer getCurrentOffer(){
        String customerId = getCurrentCustomerId();
        return sales.getCurrentOffer(customerId);
    }

    @PostMapping("/api/add-to-cart/{productId}")
    void addToCart(@PathVariable(name = "productId") String productId) {
        String customerId = getCurrentCustomerId();
        sales.addToCart(customerId, productId);
    };

    @PostMapping("/api/empty-the-cart/")
    void empty() {
        String customerId = getCurrentCustomerId();
        sales.emptyCart(customerId);
    };

    @PostMapping("/api/accept-offer")
    ReservationDetail acceptOffer(@RequestBody AcceptOfferRequest acceptOfferRequest) {
        String customerId = getCurrentCustomerId();
        ReservationDetail reservationDetail = sales.acceptOfferPayU(customerId, acceptOfferRequest);
        return  reservationDetail;
    }

    private String getCurrentCustomerId(){
        return "Mateusz";
    }
}