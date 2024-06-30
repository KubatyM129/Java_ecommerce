package pl.kubaty.ecommerce.sales.reservation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ReservationDetail {
    private final String reservationId;
    private final String paymentUrl;
    private final BigDecimal total;

    @JsonCreator
    public ReservationDetail(
            @JsonProperty("reservationId") String reservationId,
            @JsonProperty("paymentUrl") String paymentUrl,
            @JsonProperty("total") BigDecimal total){
        this.reservationId = reservationId;
        this.paymentUrl = paymentUrl;
        this.total = total;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
