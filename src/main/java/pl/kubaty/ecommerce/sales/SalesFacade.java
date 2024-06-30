package pl.kubaty.ecommerce.sales;

import pl.kubaty.ecommerce.payu.OrderCreateRequest;
import pl.kubaty.ecommerce.sales.cart.Cart;
import pl.kubaty.ecommerce.sales.cart.InMemoryCartStorage;
import pl.kubaty.ecommerce.sales.offer.AcceptOfferRequest;
import pl.kubaty.ecommerce.sales.offer.Offer;
import pl.kubaty.ecommerce.sales.offer.OfferCalculator;
import pl.kubaty.ecommerce.sales.payment.PaymentDetails;
import pl.kubaty.ecommerce.sales.payment.PaymentGateway;
import pl.kubaty.ecommerce.sales.payment.RegisterPaymentRequest;
import pl.kubaty.ecommerce.sales.reservation.Reservation;
import pl.kubaty.ecommerce.sales.reservation.ReservationDetail;
import pl.kubaty.ecommerce.sales.reservation.ReservationRepository;

import java.util.UUID;

public class SalesFacade {
    private InMemoryCartStorage cartStorage;
    private OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private ReservationRepository reservationRepository;


    public SalesFacade(InMemoryCartStorage cartStorage, OfferCalculator offerCalculator, PaymentGateway paymentGateway, ReservationRepository reservationRepository) {
        this.cartStorage = cartStorage;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationRepository = reservationRepository;
    }

    public Offer getCurrentOffer(String customerId) {
        Cart cart = loadCartForCustomer(customerId);

        Offer currentOffer = offerCalculator.calculateOffer(cart.getLines());

        return currentOffer;
    }

    public ReservationDetail acceptOffer(String customerId, AcceptOfferRequest acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();
        Offer offer = this.getCurrentOffer(customerId);

        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                RegisterPaymentRequest.of(reservationId, acceptOfferRequest, offer.getTotalAfterDiscount())
        );
        Reservation reservation = Reservation.of(
                reservationId,
                customerId,
                acceptOfferRequest,
                offer,
                paymentDetails);

        reservationRepository.add(reservation);

        return new ReservationDetail(reservationId, paymentDetails.getPaymentUrl(), offer.getTotalAfterDiscount());
    }

    public ReservationDetail acceptOfferPayU(String customerId, AcceptOfferRequest acceptOfferRequest) {
        String reservationId = UUID.randomUUID().toString();
        Offer offer = this.getCurrentOffer(customerId);

        PaymentDetails paymentDetails = paymentGateway.registerPayment(
                OrderCreateRequest.of(reservationId, acceptOfferRequest, offer.getTotalAfterDiscount(), offer)
        );
        Reservation reservation = Reservation.of(
                reservationId,
                customerId,
                acceptOfferRequest,
                offer,
                paymentDetails);

        reservationRepository.add(reservation);

        return new ReservationDetail(reservationId, paymentDetails.getPaymentUrl(), offer.getTotalAfterDiscount());
    }

    public void addToCart(String customerId, String productId) {
        Cart cart = loadCartForCustomer(customerId);
        cart.addProduct(productId);
        cartStorage.save(customerId, cart);
    }

    public void emptyCart(String customerId) {
        Cart cart = loadCartForCustomer(customerId);
        cartStorage.delete(customerId, cart);
    }


    private Cart loadCartForCustomer(String customerId) {
        return cartStorage.findByCustomer(customerId)
                .orElse(Cart.empty());
    }
}
