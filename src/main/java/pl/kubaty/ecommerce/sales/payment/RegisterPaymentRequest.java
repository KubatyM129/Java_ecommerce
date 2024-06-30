package pl.kubaty.ecommerce.sales.payment;

import pl.kubaty.ecommerce.sales.offer.AcceptOfferRequest;

import java.math.BigDecimal;

public class RegisterPaymentRequest {

    private final String reservationId;
    private final AcceptOfferRequest acceptOfferRequest;
    private final BigDecimal total;


    public RegisterPaymentRequest(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total) {

        this.reservationId = reservationId;
        this.acceptOfferRequest = acceptOfferRequest;
        this.total = total;
    }

    public static RegisterPaymentRequest of(String reservationId, AcceptOfferRequest acceptOfferRequest, BigDecimal total) {
        return new RegisterPaymentRequest(
                reservationId,
                acceptOfferRequest,
                total
        );
    }

    public String getReservationId() {
        return reservationId;
    }

    public AcceptOfferRequest getAcceptOfferRequest() {
        return acceptOfferRequest;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
